package com.gizwits.lease.plan.job;

import com.gizwits.boot.utils.DateKit;
import com.gizwits.lease.config.CronConfig;
import com.gizwits.lease.device.service.DeviceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

/**
 * Description: 设备定时计划
 * Created by Sunny on 2019/12/24 15:58
 */
@Slf4j
public class DevicePlanScheduler {


    @Autowired
    private CronConfig cronConfig;
    @Autowired
    private DeviceService deviceService;

    @Scheduled(cron = "#{cronConfig.getEveryMin}")
    public void startDevicePlan() {
        log.info("开始startDevicePlan时间为：" + DateKit.getTimestampString(new Date()));
        deviceService.sendPlanToDevices();

        log.info("结束startDevicePlan时间为：" + DateKit.getTimestampString(new Date()));
    }
}
