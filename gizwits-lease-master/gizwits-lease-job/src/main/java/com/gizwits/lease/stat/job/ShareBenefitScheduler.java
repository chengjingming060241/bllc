package com.gizwits.lease.stat.job;

import com.gizwits.lease.benefit.service.ShareBenefitSheetService;
import com.gizwits.lease.config.CronConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by zhl on 2017/8/4.
 */
@Component
public class ShareBenefitScheduler {

    private static Logger logger = LoggerFactory.getLogger("BENEFIT_LOGGER");

    @Autowired
    private ShareBenefitSheetService shareBenefitSheetService;


    @Autowired
    private CronConfig cronConfig;


    /**
     * 生成分润账单
     * 1.查询系统中所有可用的分润规则
     * 2.判断分润规则是否到了执行时间
     * 3.加载分润规则中相应设备的订单
     * 4.计算并生成分润单
     */
    @Scheduled(cron = "#{cronConfig.getEveryFiveMin()}")
//    @Scheduled(cron = "0 */2 * * * ?")
    public void schedulerShareBenefit(){
        shareBenefitSheetService.executeAllShareBenefit();
    }

}
