package com.gizwits.lease.order.job;

import com.gizwits.boot.utils.DateKit;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.config.CronConfig;
import com.gizwits.lease.constant.OrderStatus;
import com.gizwits.lease.message.entity.MessageTemplate;
import com.gizwits.lease.message.entity.SysMessage;
import com.gizwits.lease.message.service.MessageTemplateService;
import com.gizwits.lease.message.service.SysMessageService;
import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.product.service.ProductService;
import com.gizwits.lease.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Description:
 * User: yinhui
 * Date: 2018-01-12
 */
//@Component
public class OrderLeaseRemindScheduler {

    protected static Logger logger = LoggerFactory.getLogger(OrderLeaseRemindScheduler.class);

    @Autowired
    private RedisService redisService;

    @Autowired
    private MessageTemplateService messageTemplateService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SysMessageService sysMessageService;

    @Autowired
    private CronConfig cronConfig;

    @Scheduled(cron = "#{cronConfig.getEveryMin()}")
    public void sendMessage() {
        Date now = new Date();
        logger.info("发送订单租赁提醒消息 时间 = " + DateKit.getTimestampString(new Date()));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String time = sdf.format(now);
        List<OrderBase> orderBases = redisService.getOrderLeaseRemind(time);
        if (!ParamUtil.isNullOrEmptyOrZero(orderBases)) {
            for (OrderBase orderBase : orderBases) {
                if (!ParamUtil.isNullOrEmptyOrZero(orderBase) && !orderBase.equals(OrderStatus.FINISH.getCode())) {
                    Product product = productService.getProductByDeviceSno(orderBase.getSno());
                    MessageTemplate messageTemplate = messageTemplateService.getByProductIdAndServiceId(product.getId(), orderBase.getServiceModeId());
                    if (!ParamUtil.isNullOrEmptyOrZero(messageTemplate)) {
                        logger.info("发送订单租赁提醒消息 时间 = " + time + ",订单 orderNo = " + orderBase.getOrderNo());
                        SysMessage sysMessage = messageTemplateService.sendSysMessage(orderBase, messageTemplate);
                        sysMessage.setIsFixed(1);
                        sysMessageService.updateById(sysMessage);
                        redisService.deleteOrderLeaseRemind(time);
                    }
                }
            }
        }
        logger.info("结束发送订单租赁提醒消息 时间 = " + DateKit.getTimestampString(new Date()));
    }
}