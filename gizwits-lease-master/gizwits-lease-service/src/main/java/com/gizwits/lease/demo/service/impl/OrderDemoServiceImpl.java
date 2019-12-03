package com.gizwits.lease.demo.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.enums.DeleteStatus;


import com.gizwits.lease.constant.DeviceStatus;
import com.gizwits.lease.constant.OrderStatus;
import com.gizwits.lease.constant.ThirdPartyUserType;
import com.gizwits.lease.demo.service.OrderDemoService;
import com.gizwits.lease.demo.service.RandomDemoService;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.enums.DeviceOriginType;
import com.gizwits.lease.manager.entity.Operator;
import com.gizwits.lease.order.dto.PayOrderDto;
import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.order.entity.OrderStatusFlow;
import com.gizwits.lease.order.service.OrderBaseService;
import com.gizwits.lease.order.service.OrderStatusFlowService;
import com.gizwits.lease.order.vo.AppOrderVo;
import com.gizwits.lease.product.dto.AppServiceModeDetailDto;
import com.gizwits.lease.product.service.ProductServiceDetailService;
import com.gizwits.lease.product.vo.AppProductServiceDetailVo;
import com.gizwits.lease.product.vo.ProductServiceDetailVo;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.service.UserService;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Random;


@Service
public class OrderDemoServiceImpl implements OrderDemoService {

    private static final Logger logger = LoggerFactory.getLogger(OrderDemoServiceImpl.class);

    @Autowired
    private RandomDemoService randomDemoService;

    @Autowired
    private OrderBaseService orderBaseService;

    @Autowired
    private ProductServiceDetailService productServiceDetailService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private OrderStatusFlowService orderStatusFlowService;


    @Autowired
    private UserService userService;

   

    @Override
    public int demoGenerateOrders(Date time) {
        Date now = new Date();
        finishOrders(time);

        Date currentHourTime = randomDemoService.getOclockTime(time, 0);
        Date lastHourTime = DateUtils.addHours(currentHourTime, -1);
        int deviceCount = countAvailableDeviceForOrder(lastHourTime);
        logger.info("generate orders available device count {}", deviceCount);
        int orderCount = Math.max(1, deviceCount * 3 / 100 + new Random().nextInt(11) - 5);
        if (orderCount > deviceCount) {
            orderCount = deviceCount;
        }
        logger.info("try to generate orders count {} at {}", orderCount, time);
        int successCount = 0;
        for (int i = 0; i < orderCount; i++) {
            try {
                boolean result = generateOrder(lastHourTime, currentHourTime);
                if (result) {
                    successCount++;
                }
            } catch (Exception e) {
                logger.error("generate orders fail", e);
            }
        }
        long minutes = (new Date().getTime() - now.getTime()) / 1000 / 60;
        logger.info("generate orders success count {} begin at {} use {} mins", successCount, time, minutes);
        return successCount;
    }

    @Override
    public boolean generateOrder(Date startTime, Date endTime) {
        Device device = randomDemoService.getRandomDevice(getDeviceWrapper(startTime));
        if (device == null) {
            logger.warn("no available device for generate orders");
            return false; // no device for generate order
        }
        logger.info("generate orders by device {}", device.getSno());

        User user = getRandomUser();
        if (user == null) {
            logger.warn("no available user for generate orders");
            return false; // no user for generate order
        }
        logger.info("generate orders by user {}", user.getId());

        // random service mode detail
        Integer productServiceModeDetailId = getRandomServiceModeDetail(device);
        if (productServiceModeDetailId == null) {
            logger.warn("no available service mode for generate orders");
            return false; // no service mode detail for generate order
        }
        logger.info("generate orders by service_mode_detail {}", productServiceModeDetailId);

        PayOrderDto payOrderDto = new PayOrderDto();
        payOrderDto.setSno(device.getSno());
        payOrderDto.setProductServiceDetailId(productServiceModeDetailId);
        payOrderDto.setServiceModeId(device.getServiceId());
        AppOrderVo order = orderBaseService.createOrder(user, payOrderDto, ThirdPartyUserType.WEIXIN.getCode());
        logger.info("generate orders {} success", order.getOrderNo());

        // random order time
        Date orderCtime = randomDemoService.randomTime(startTime, endTime);
        if (orderCtime == null) {
            logger.warn("no available time for generate orders");
            return false; // no fit time for generate order
        }
        OrderBase orderForUpdate = new OrderBase();
        orderForUpdate.setOrderNo(order.getOrderNo());
        orderForUpdate.setCtime(orderCtime);
        orderForUpdate.setPayTime(orderCtime);
        orderForUpdate.setRemark("aep");
        orderBaseService.updateById(orderForUpdate);

        OrderStatusFlow orderStatusFlow = new OrderStatusFlow();
        orderStatusFlow.setCtime(orderCtime);
        Wrapper<OrderStatusFlow> wrapper = new EntityWrapper<>();
        wrapper.eq("order_no", order.getOrderNo());
        wrapper.eq("now_status", OrderStatus.INIT.getCode());
        orderStatusFlowService.update(orderStatusFlow, wrapper);


        // update device status to using
        Device deviceForUpdate = new Device();
        deviceForUpdate.setSno(device.getSno());
        deviceForUpdate.setWorkStatus(DeviceStatus.USING.getCode());
        deviceService.updateById(deviceForUpdate);
        return true;
    }

    private Wrapper<Device> getDeviceWrapper(Date time) {
        Wrapper<Device> wrapper = new EntityWrapper<>();
        wrapper.eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        wrapper.eq("origin", 3); // 随机生成的设备
        wrapper.isNotNull("service_id");
        wrapper.isNotNull("launch_area_id");
        wrapper.in("owner_id", getOperatorSysAccountIds());
//        wrapper.eq("online_status", DeviceOnlineStatus.ONLINE.getCode());
        wrapper.eq("work_status", DeviceStatus.FREE.getCode());
        wrapper.lt("ctime", time);
        return wrapper;
    }

    private List<Integer> getOperatorSysAccountIds() {

        return null;
    }

    @Override
    public int countAvailableDeviceForOrder(Date time) {
        Wrapper<Device> wrapper = getDeviceWrapper(time);
        return deviceService.selectCount(wrapper);
    }

    private Integer getRandomServiceModeDetail(Device device) {
        AppServiceModeDetailDto appServiceModeDetailDto = new AppServiceModeDetailDto();
        appServiceModeDetailDto.setSno(device.getSno());
        AppProductServiceDetailVo serviceModeDetailList = productServiceDetailService.getListForApp(appServiceModeDetailDto);
        if (serviceModeDetailList.getList().size() == 0) {
            return null;
        }
        ProductServiceDetailVo productServiceDetailVo = serviceModeDetailList.getList().get(new Random().nextInt(serviceModeDetailList.getList().size()));
        return productServiceDetailVo.getId();
    }

    private User getRandomUser() {
        Wrapper<User> wrapper = new EntityWrapper<>();
        wrapper.eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        wrapper.eq("remark", "aep");
        int count = userService.selectCount(wrapper);
        if (count == 0) {
            return null;
        }
        Page<User> page = new Page<>();
        page.setSize(1);
        page.setCurrent(new Random().nextInt(count) + 1);
        page = userService.selectPage(page, wrapper);
        if (page.getRecords().size() > 0) {
            return page.getRecords().get(0);
        } else {
            return null;
        }
    }




    @Override
    public void finishOrders(Date refTime) {
        Date endTime = randomDemoService.getOclockTime(refTime, -1);
        Date startTime = DateUtils.addHours(endTime, -1);

        Date orderTime = DateUtils.addHours(endTime, -9);
        Wrapper<OrderBase> wrapper = new EntityWrapper<>();
        wrapper.eq("order_status", OrderStatus.USING.getCode());
        wrapper.lt("ctime", orderTime);
        wrapper.setSqlSelect("order_no, sno");
        List<OrderBase> orders = orderBaseService.selectList(wrapper);
        logger.info("using order count {}", orders.size());
        int finishCount = 0;
        for (OrderBase order : orders) {
            Device device = deviceService.selectById(order.getSno());
            if (device == null) {
                OrderBase orderForUpdate = new OrderBase();
                orderForUpdate.setOrderNo(order.getOrderNo());
                orderForUpdate.setOrderStatus(OrderStatus.EXPIRE.getCode());
                orderBaseService.updateById(orderForUpdate);
            } else {
                if (device.getOrigin().equals(DeviceOriginType.RANDOM.getCode())) {
                    Date finishTime = randomDemoService.randomTime(startTime, endTime);

                    Device deviceForUpdate = new Device();
                    deviceForUpdate.setSno(order.getSno());
                    deviceForUpdate.setWorkStatus(DeviceStatus.FREE.getCode());
                    deviceService.updateById(deviceForUpdate);
                    finishCount++;
                }
            }
        }
        logger.info("finish order {}", finishCount);
    }

    private Date getLastOrderFinishTime(Device device) {
        Wrapper<OrderBase> wrapper = new EntityWrapper<>();
        wrapper.eq("sno", device.getSno());
        wrapper.orderBy("ctime", false);
        OrderBase orderBase = orderBaseService.selectOne(wrapper);
        if (orderBase == null) {
            return null;
        } else {
            return orderBase.getUtime();
        }
    }
}
