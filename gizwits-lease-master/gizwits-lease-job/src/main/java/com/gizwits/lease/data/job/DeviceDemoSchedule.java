package com.gizwits.lease.data.job;

import com.gizwits.lease.config.CronConfig;
import com.gizwits.lease.demo.service.OrderDemoService;
import com.gizwits.lease.device.service.DeviceDemoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Description:
 * User: Joke
 * Date: 2018-03-02
 */
//@Component
public class DeviceDemoSchedule {

    private static Logger logger = LoggerFactory.getLogger(DeviceDemoSchedule.class);

    @Autowired
    private CronConfig cronConfig;
    @Autowired
    private DeviceDemoService deviceDemoService;
    @Autowired
    private OrderDemoService orderDemoService;

    // @Scheduled(cron = "#{cronConfig.getEveryHour()}")
    public void makeData() {
        deviceDemoService.makeData(new Date(), 2,3);
        logger.info("设备数据生成结束");

        orderDemoService.demoGenerateOrders(new Date());
        logger.info("订单数据生成结束");
    }


}
