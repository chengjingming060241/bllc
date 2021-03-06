package com.gizwits.lease.trade.service;


import com.alipay.api.response.AlipayTradeRefundResponse;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.boot.sys.entity.SysUserExt;
import com.gizwits.lease.order.dto.PrePayDto;
import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.trade.entity.TradeAlipay;
import com.gizwits.lease.trade.entity.TradeBase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;

/**
 * <p>
 * 支付宝交易表 服务类
 * </p>
 *
 * @author yinhui
 * @since 2017-08-15
 */
public interface TradeAlipayService extends IService<TradeAlipay> {



    /**
     * 支付宝支付
     * @param prePayDto
     * @return
     */
    String prePay(PrePayDto prePayDto, Integer browserAgentType);


    String precrate(SysUserExt sysUserExt, String orderNo, BigDecimal fee, String subject, String storeId);

    /**
     * 执行支付
     * @param sysUserExt
     * @param orderNo
     * @param fee
     * @param subject
     * @param body
     * @param buyerId
     * @return
     */
    String executePay(SysUserExt sysUserExt, String orderNo, BigDecimal fee, String subject, String body, String buyerId);

    /**
     * 获取最后交易
     * @param orderNo
     * @return
     */
    TradeAlipay selectLastTrade(String orderNo);

    /**
     * 支付回调
     * @param request
     * @param response
     */
    void alipayNotify(HttpServletRequest request, HttpServletResponse response);

    /**
     *支付状态查询
     * @param orderBase
     * @return
     */
    boolean executePayStatusQuery(OrderBase orderBase);
    void transfer(String orderNo, String subject, Double amount, String account, String realName, Integer sysUserId);
    /**
     * 支付宝退款
     * @param
     * @return
     */
    AlipayTradeRefundResponse refund(OrderBase orderBase,String tradeNo);

    void refund(OrderBase orderBase, TradeBase tradeBase);
//
    AlipayTradeRefundResponse baseRefund(String tradeNo, String outTradeNo, Double refundMoney, String refundMsg, SysUserExt sysUserExt);
}
