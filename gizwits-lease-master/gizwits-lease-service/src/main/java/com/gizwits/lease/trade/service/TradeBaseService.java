package com.gizwits.lease.trade.service;

import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.trade.entity.TradeBase;
import com.baomidou.mybatisplus.service.IService;

import java.math.BigDecimal;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author gagi
 * @since 2017-07-29
 */
public interface TradeBaseService extends IService<TradeBase> {
    /**
     * 根据订单和支付回调路径
     *
     * @param orderNo
     * @param fee
     * @param notifyUrl @return tradeBase
     * @param tradeType
     * @param orderType
     */
    TradeBase createTrade(String orderNo, Double fee, String notifyUrl, Integer tradeType, Integer orderType);

    TradeBase createTradeBase(String orderNo, Double fee, String notifyUrl, Integer tradeType, Integer orderType);

    /**
     * 将tradeBase里面的状态更改为toStatus
     *
     * @param tradeBase
     * @param toStatus
     * @return 修改成功与否
     */
    Boolean updateTradeStatus(TradeBase tradeBase, Integer toStatus);

    /**
     * 将tradeNo对应的tradeBase里面的状态更改为toStatus
     *
     * @param tradeNo
     * @param toStatus
     * @return
     */
    Boolean updateTradeStatus(String tradeNo, Integer toStatus);

    /**
     * 根据交易号查找
     *
     * @param tradeNo
     * @return
     */
    TradeBase selectByTradeNo(String tradeNo);

    /**
     * 执行退款操作，退款操作可以重复执行
     *
     * @param orderBase
     * @param refundMoney 退款金额，单位为元，必须大于0
     * @Param refundVersion 当前退款版本号
     */
    void refund(OrderBase orderBase, BigDecimal refundMoney, Integer refundVersion);
}
