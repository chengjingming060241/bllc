package com.gizwits.lease.demo.service;

import java.util.Date;

public interface OrderDemoService {

    /**
     * 模拟订单数据
     */
    int demoGenerateOrders(Date time);

    /**
     * 查询可生成订单的设备数量
     */
    int countAvailableDeviceForOrder(Date time);

    /**
     * 指定创建时间生成随机订单
     */
    boolean generateOrder(Date startTime, Date endTime);

    /**
     * 随机结束订单
     */
    void finishOrders(Date time);
}
