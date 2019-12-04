package com.gizwits.lease.weather.job;


import com.gizwits.lease.device.service.DeviceExtWeatherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 *  更新设备天气
 */

//@Component
public class DeviceWeatherScheduler {

    @Autowired
    private DeviceExtWeatherService deviceExtWeatherService;

    protected static Logger logger = LoggerFactory.getLogger(DeviceWeatherScheduler.class);


/*
    @Scheduled(cron = "#{cronConfig.getDeviceWeather()}")
    public void startOrderAnalysis() {

        logger.info(String.format("开始更新设备天气时间为：%s", LocalDateTime.now().toString()));

        deviceExtWeatherService.updateWeather();

        logger.info(String.format("结束更新设备天气时间为：%s", LocalDateTime.now().toString()));

        logger.info(String.format("开始下发数据点更新设备天气时间为：%s", LocalDateTime.now().toString()));
        deviceExtWeatherService.sendDeviceWeather();
        logger.info(String.format("完成设备天气更新时间为：%s", LocalDateTime.now().toString()));
    }
*/

}
