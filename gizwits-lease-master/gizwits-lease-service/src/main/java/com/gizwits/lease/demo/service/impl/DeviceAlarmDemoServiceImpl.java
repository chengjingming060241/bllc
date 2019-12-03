package com.gizwits.lease.demo.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.lease.constant.AlarmStatus;
import com.gizwits.lease.constant.AlarmType;
import com.gizwits.lease.constant.DeviceStatus;
import com.gizwits.lease.constant.OrderStatus;
import com.gizwits.lease.demo.service.DeviceAlarmDemoService;
import com.gizwits.lease.demo.service.RandomDemoService;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.DeviceAlarm;
import com.gizwits.lease.device.service.DeviceAlarmService;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.enums.DeviceOriginType;
import com.gizwits.lease.enums.ReadWriteType;
import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.product.entity.ProductDataPoint;
import com.gizwits.lease.product.service.ProductDataPointService;
import com.gizwits.lease.product.service.ProductService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class DeviceAlarmDemoServiceImpl implements DeviceAlarmDemoService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceAlarmDemoServiceImpl.class);

    @Autowired
    private RandomDemoService randomDemoService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceAlarmService deviceAlarmService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDataPointService productDataPointService;

    private Wrapper<Device> getDeviceWrapper(Date time) {
        Wrapper<Device> wrapper = new EntityWrapper<>();
        wrapper.eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        wrapper.eq("origin", DeviceOriginType.RANDOM.getCode()); // 随机生成的设备
        wrapper.lt("ctime", time); // 随机生成的设备
        return wrapper;
    }

    @Override
    public int countDeviceForDemoAlarm(Date time) {
        Wrapper<Device> wrapper = getDeviceWrapper(time);
        return deviceService.selectCount(wrapper);
    }

    @Override
    public int demoGenerateAlarms(Date time) {
        fixDeviceAlarms(time);

        Date currentHourTime = randomDemoService.getOclockTime(time, 0);
        Date lastHourTime = DateUtils.addHours(currentHourTime, -1);
        int deviceCount = countDeviceForDemoAlarm(lastHourTime);
        logger.info("available for demo alarm device count {}", deviceCount);

        int alarmCount = Math.max(2, deviceCount / 100 + new Random().nextInt(11) - 5);
        logger.info("try to generate {} alarms", alarmCount);

        int count = 0;
        for (int i = 0; i < alarmCount; i++) {
            try {
                boolean result = generateDeviceAlarm(lastHourTime, currentHourTime);
                if (result) {
                    count++;
                }
            } catch (Exception e) {
                logger.error("generateDeviceAlarm fail", e);
            }
        }
        logger.info("generate alarms success count {} at {}", count, time);
        return count;
    }

    private boolean generateDeviceAlarm(Date startTime, Date endTime) {
        Device device = randomDemoService.getRandomDevice(getDeviceWrapper(startTime));
        if (device == null) {
            logger.warn("no device for demo alarm");
            return false;
        }

        Product product = productService.selectById(device.getProductId());
        if (product == null) {
            logger.warn("product not exist for device {}", device.getSno());
            return false;
        }

        ProductDataPoint alarmDataPoint = getRandomAlarmDataPoint(product);
        if (alarmDataPoint == null) {
            logger.warn("no alarm data point for product {}", product.getId());
            return false;
        }

        // random time
        Date alarmTime = randomDemoService.randomTime(startTime, endTime);
        if (alarmTime == null) {
            logger.warn("no available time for demo alarm");
            return false;
        }

        DeviceAlarm alarm = new DeviceAlarm();
        alarm.setCtime(alarmTime);
        alarm.setHappenTime(alarmTime);
        alarm.setAlarmType(getAlarmType(alarmDataPoint).getCode());
        alarm.setName(alarmDataPoint.getShowName());
        alarm.setAttr(alarmDataPoint.getIdentityName());
        alarm.setStatus(AlarmStatus.UNRESOLVE.getCode());
        alarm.setMac(device.getMac());
        alarm.setSno(device.getSno());
        alarm.setLongitude(device.getLongitude());
        alarm.setLatitude(device.getLatitude());
        alarm.setProductKey(product.getGizwitsProductKey());
        alarm.setRemark("aep");
        deviceAlarmService.insert(alarm);

        Device deviceForUpdate = new Device();
        deviceForUpdate.setSno(device.getSno());
        deviceForUpdate.setFaultStatus(DeviceStatus.FAULT.getCode());
        deviceForUpdate.setUtime(alarmTime);
        deviceService.updateById(deviceForUpdate);
        return true;
    }

    @Override
    public void fixDeviceAlarms(Date time) {
        Date endTime = randomDemoService.getOclockTime(time, -1);
        Date startTime = DateUtils.addHours(endTime, -1);
        Date alarmTime = DateUtils.addHours(endTime, -9);

        Wrapper<DeviceAlarm> wrapper = new EntityWrapper<>();
        wrapper.eq("status", AlarmStatus.UNRESOLVE.getCode());
        wrapper.lt("ctime", alarmTime);
        wrapper.eq("remark", "aep");
        wrapper.setSqlSelect("id, sno");
        List<DeviceAlarm> alarms = deviceAlarmService.selectList(wrapper);
        for (DeviceAlarm alarm : alarms) {
            DeviceAlarm deviceAlarmForUpdate = new DeviceAlarm();
            deviceAlarmForUpdate.setId(alarm.getId());
            deviceAlarmForUpdate.setStatus(AlarmStatus.RESOLVE.getCode());
            Date fixedTime = randomDemoService.randomTime(startTime, endTime);
            deviceAlarmForUpdate.setFixedTime(fixedTime);
            deviceAlarmService.updateById(deviceAlarmForUpdate);

            Device deviceForUpdate = new Device();
            deviceForUpdate.setSno(alarm.getSno());
            deviceForUpdate.setFaultStatus(DeviceStatus.NORMAL.getCode());
            deviceForUpdate.setUtime(fixedTime);
            deviceService.updateById(deviceForUpdate);
        }
    }

    private ProductDataPoint getRandomAlarmDataPoint(Product product) {
        Wrapper<ProductDataPoint> wrapper = new EntityWrapper<>();
        wrapper.eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        wrapper.eq("product_id", product.getId());
        wrapper.in("read_write_type", Arrays.asList(ReadWriteType.ALERT.getCode(), ReadWriteType.FAULT.getCode()));

        int count = productDataPointService.selectCount(wrapper);
        if (count == 0) {
            return null;
        }
        Page<ProductDataPoint> page = new Page<>();
        page.setSize(1);
        page.setCurrent(new Random().nextInt(count) + 1);
        page = productDataPointService.selectPage(page, wrapper);
        if (page.getRecords().size() > 0) {
            return page.getRecords().get(0);
        } else {
            return null;
        }
    }

    private AlarmType getAlarmType(ProductDataPoint dataPoint) {
        if (dataPoint.getReadWriteType().equals(ReadWriteType.ALERT.getCode())) {
            return AlarmType.ALERT;
        } else {
            return AlarmType.FLAUT;
        }
    }
}
