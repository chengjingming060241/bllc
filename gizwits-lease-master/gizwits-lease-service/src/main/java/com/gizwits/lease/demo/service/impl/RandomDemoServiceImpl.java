package com.gizwits.lease.demo.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.lease.demo.service.RandomDemoService;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.order.service.OrderStatusFlowService;
import com.gizwits.lease.user.service.UserService;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Service
public class RandomDemoServiceImpl implements RandomDemoService {

    private static final Logger logger = LoggerFactory.getLogger(RandomDemoServiceImpl.class);

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private UserService userService;

    @Override
    public Date getOclockTime(Date date, int amount) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, amount);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date clockTime = calendar.getTime();
        logger.info("get oclock time {}", clockTime);
        return clockTime;
    }

    @Override
    public Date randomTime(Date from, Date to) {
        long diffSeconds = (to.getTime() - from.getTime()) / 1000;
        if (diffSeconds <= 0) {
            return null;
        }
        int randomSeconds = new Random().nextInt((int) diffSeconds);
        return DateUtils.addSeconds(from, randomSeconds);
    }

    @Override
    public Device getRandomDevice(Wrapper<Device> wrapper) {
        int count = deviceService.selectCount(wrapper);
        if (count == 0) {
            return null;
        }
        Page<Device> page = new Page<>();
        page.setSize(1);
        page.setCurrent(new Random().nextInt(count) + 1);
        page = deviceService.selectPage(page, wrapper);
        if (page.getRecords().size() > 0) {
            return page.getRecords().get(0);
        } else {
            return null;
        }
    }
}
