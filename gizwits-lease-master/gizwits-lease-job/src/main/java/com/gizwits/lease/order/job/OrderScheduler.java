package com.gizwits.lease.order.job;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gizwits.boot.utils.DateKit;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.card.entity.UserCard;
import com.gizwits.lease.card.service.UserCardService;
import com.gizwits.lease.constant.OrderStatus;
import com.gizwits.lease.constant.UserWalletChargeOrderType;
import com.gizwits.lease.enums.OrderEndType;
import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.order.entity.OrderExtByQuantity;
import com.gizwits.lease.order.entity.OrderStatusFlow;
import com.gizwits.lease.order.entity.OrderTimer;
import com.gizwits.lease.order.service.*;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.product.service.ProductService;
import com.gizwits.lease.redis.RedisService;
import com.gizwits.lease.wallet.entity.UserWalletChargeOrder;
import com.gizwits.lease.wallet.service.UserWalletChargeOrderService;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by GaGi on 2017/8/26.
 */
@Component
public class OrderScheduler {
    @Autowired
    private OrderBaseService orderBaseService;
    @Autowired
    private OrderTimerService orderTimerService;
    @Autowired
    private OrderExtByQuantityService orderExtByQuantityService;
    @Autowired
    private OrderStatusFlowService orderStatusFlowService;
    @Autowired
    private OrderDataFlowService orderDataFlowService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserCardService userCardService;

    @Autowired
    private UserWalletChargeOrderService userWalletChargeOrderService;

    protected static Logger logger = LoggerFactory.getLogger(OrderScheduler.class);



 @Scheduled(cron = "#{cronConfig.getEveryFiveMin()}")
    public void startOrderChangeExpired() {
        //查询订单状态为init和paying的订单
        List<Integer> status = new ArrayList<>();
        status.add(OrderStatus.INIT.getCode());
        status.add(OrderStatus.PAYING.getCode());
        List<OrderBase> list = orderBaseService.selectList(new EntityWrapper<OrderBase>().in("order_status", status));
        Date now = new Date();
        for (int i = 0; i < list.size(); ++i) {
            OrderBase orderBase = list.get(i);
            Date orderTime = orderBase.getCtime();
            Date expiredTime = DateKit.addMinute(orderTime, 60);
            if (now.after(expiredTime)) {
                orderBaseService.updateOrderStatusAndHandle(orderBase, OrderStatus.EXPIRE.getCode());
                List<OrderTimer> orderTimerList = orderTimerService.getOrderTimersByOrderNo(orderBase.getOrderNo());
                for (OrderTimer orderTimer : orderTimerList) {
                    logger.info(orderBase.getOrderNo() + "的定时器失效");
                    orderTimer.setIsExpire(1);
                    orderTimerService.updateById(orderTimer);
                }
            }
        }
    }

    /**
     * 设备离线后，需要后台检查订单是否超过租赁时间，超过标记为已完成
     */
    @Scheduled(cron = "#{cronConfig.getEveryFiveMin()}")
    public void startOrderUseExpired() {
        logger.info("[检查订单是否超过租赁时间] 开始！");
        //查出所有使用中订单
        Wrapper<OrderBase> wrapper = new EntityWrapper<>();
        wrapper.eq("order_status", OrderStatus.USING.getCode());
        List<OrderBase> list = orderBaseService.selectList(wrapper);
        list.forEach(orderBase -> {
            try {
                logger.info("[检查订单是否超过租赁时间] 开始处理订单号:{}", orderBase.getOrderNo());
                //查出订单的有效时间
                Wrapper<OrderStatusFlow> flowWrapper = new EntityWrapper<>();
                flowWrapper.eq("order_no", orderBase.getOrderNo()).eq("now_status", OrderStatus.USING.getCode());
                OrderStatusFlow orderStatusFlow = orderStatusFlowService.selectOne(flowWrapper);
                OrderExtByQuantity orderExtByQuantity = orderExtByQuantityService.selectById(orderBase.getOrderNo());
                String unit = orderExtByQuantity.getUnit();
                long startTime = orderStatusFlow.getCtime().getTime();
                long leaseTime = (long) (orderExtByQuantity.getQuantity() * 1000);
                switch (unit) {
                    default:
                        logger.warn("[检查订单是否超过租赁时间] 订单{}未能识别时间单位:{}", orderBase.getOrderNo(), unit);
                        return;
                    case "年":
                        leaseTime *= 365 * 24 * 60 * 60;
                        break;
                    case "月":
                        leaseTime *= 30;
                    case "天":
                        leaseTime *= 24;
                    case "时":
                    case "小时":
                        leaseTime *= 60;
                    case "分":
                    case "分钟":
                        leaseTime *= 60;
                        break;
                    case "次":
                        leaseTime *= 5 * 60;   //测试这里暂定一次5分钟
                }
                long now = System.currentTimeMillis();
                Date endTime = new Date(startTime + leaseTime);
                logger.info("[检查订单是否超过租赁时间] 订单号:{}，开始时间:{}，租赁量:{}，租赁单位:{}，原定结束时间:{}",
                        orderBase.getOrderNo(), orderStatusFlow.getCtime(), orderExtByQuantity.getQuantity(), unit, endTime);
                if (now > endTime.getTime()) {
                    //更新为完成
                    logger.info("订单超出租赁时间，需要更新为已完成");
                    // 设置订单结束方式为定时器结束
                    orderBase.setEndType(OrderEndType.END_BY_JOB.getCode());
                    orderBaseService.updateOrderStatusAndHandle(orderBase, OrderStatus.FINISH.getCode());

                    orderDataFlowService.saveFinishData(orderBase, "finished by scheduler");
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("Available_Time", 0);
//                    Product product = productService.getProductByDeviceSno(orderBase.getSno());
//                    if (!ParamUtil.isNullOrEmptyOrZero(product)) {
//                        redisService.cacheDeviceControlCommand(product.getGizwitsProductKey(), orderBase.getMac(), jsonObject.toString());
//                    }
                }
            } catch (Exception e) {
                logger.error("[检查订单是否超过租赁时间] 出错：", e);
            }
        });

        logger.info("[检查订单是否超过租赁时间] 结束。");
    }


    /**
     * 每天凌晨执行
     * 检查用户卡券是否过期
     * 卡券状态 1:正常 2:已使用 3:已失效
     */
    @Scheduled(cron = "#{cronConfig.getDaily()}")
    public void CheckIfTheCardIsExpired(){
        logger.info("-CheckIfTheCardIsExpired-检查用户卡券是否过期 开始----");
        List<UserCard> userCards = userCardService.selectList(new EntityWrapper<UserCard>()
                .eq("status", 1)
                .lt("end_time", new Date()));

        if (null != userCards && userCards.size()>0){
            for (UserCard userCard : userCards) {
                userCard.setStatus(3);
                userCardService.updateById(userCard);
                logger.info("ID为:【"+userCard.getUserId()+"】的用户卡券过期【卡券ID："+userCard.getCardId()+"】,修改过期的卡状态成功");
            }
        }

        logger.info("-CheckIfTheCardIsExpired-检查用户卡券是否过期 结束----");
    }


    /**
     * 每10分钟执行一次
     * 检查代充订单是否完成
     * 未完成记标为过期订单
     */
//    @Scheduled(cron = "0 */2 * * * ?")
    @Scheduled(cron = "#{cronConfig.getEveryTenMin()}")
    public void WhetherTheChargingOrderIsCompleted(){
        logger.info("-WhetherTheChargingOrderIsCompleted-检查代充订单 开始----");
        Date now = new Date();
        List<UserWalletChargeOrder> status = userWalletChargeOrderService.selectList(new EntityWrapper<UserWalletChargeOrder>()
                .eq("status", UserWalletChargeOrderType.PAYING.getCode()));

        if (null != status && status.size()>0){
            for (UserWalletChargeOrder userWallet : status) {
               if ((now.getTime() - userWallet.getCtime().getTime())>10){
                   userWalletChargeOrderService.updateChargeOrderStatus(userWallet, UserWalletChargeOrderType.EXPIRE.getCode());
                   logger.info("ID为:【"+userWallet.getChargeOrderNo()+"】的代充订单10分钟未付款，修改订单状态为过期！");
                }
            }
        }
        logger.info("-WhetherTheChargingOrderIsCompleted-检查代充订单 结束----");
    }
}
