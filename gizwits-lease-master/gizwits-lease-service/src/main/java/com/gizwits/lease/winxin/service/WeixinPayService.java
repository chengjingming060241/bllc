package com.gizwits.lease.winxin.service;

import com.gizwits.boot.sys.entity.SysUserExt;
import com.gizwits.lease.benefit.entity.ShareBenefitSheet;

import java.util.Map;

public interface WeixinPayService {

    /**
     * 生成二维码
     * @param out_trade_no
     * @param total_fee
     * @return
     */
    Map createNative(String out_trade_no, String total_fee);

    /**
     * 查询支付订单状态
     * @param out_trade_no
     * @return
     */
    Map queryPayStatus(String out_trade_no);


    /**
     * 微信扫码退款
     * @param out_trade_no
     */
    Boolean scanningRefund(String out_trade_no);

    /**
     * 分润打款
     * @param shareBenefitSheet
     * @param ip
     * @param sysUserExt
     * @param actionUserId
     * @return
     */
    String postShareBenefitOrder(ShareBenefitSheet shareBenefitSheet, String ip, SysUserExt sysUserExt, Integer actionUserId) throws Exception;
}
