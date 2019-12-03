package com.gizwits.lease.demo.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gizwits.lease.device.entity.Device;

import java.util.Date;

public interface RandomDemoService {

    /**
     * 获取整点时间
     */
    Date getOclockTime(Date date, int amount);

    /**
     * 随机时间
     */
    Date randomTime(Date from, Date to);

    /**
     * 获取随机设备
     */
    Device getRandomDevice(Wrapper<Device> wrapper);
}
