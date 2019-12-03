package com.gizwits.lease.wallet.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.lease.order.dto.ChargeOrderDto;
import com.gizwits.lease.order.dto.DepositOrderDto;
import com.gizwits.lease.wallet.dto.DepositListDto;
import com.gizwits.lease.wallet.dto.DepositQueryDto;
import com.gizwits.lease.wallet.dto.RefundDto;
import com.gizwits.lease.wallet.dto.UserWalletChargeListDto;
import com.gizwits.lease.wallet.dto.UserWalletChargeOrderQueryDto;
import com.gizwits.lease.wallet.entity.UserWalletChargeOrder;

import java.util.Date;

/**
 * <p>
 * 用户钱包充值单表 服务类
 * </p>
 *
 * @author yinhui
 * @since 2017-07-31
 */
public interface UserWalletChargeOrderService extends IService<UserWalletChargeOrder> {
    /**
     * 创建充值订单
     * @param chargeOrderDto
     * @return
     */
    UserWalletChargeOrder createRechargeOrder(ChargeOrderDto chargeOrderDto);



    UserWalletChargeOrder createDepositOrder(DepositOrderDto depositOrderDto);

    void updateChargeOrderStatus(String chargeOrderNo, Integer toStatus);

    /**
     * 更新充值单
     * @param totalFee
     * @return
     */
    public Boolean checkAndUpdateChargeOrder(Double totalFee, String orderNo,Integer payType,String tradeNo);

    void updateChargeOrderStatus(UserWalletChargeOrder userWalletChargeOrder, Integer toStatus);

    /**
     * 线下代充微信扫码退款
     * @param userWalletChargeOrder
     * @param toStatus
     */
    void updateCardTicketStatus(UserWalletChargeOrder userWalletChargeOrder, Integer toStatus);

    /**
     * 充值单列表
     * @param pageable
     * @return
     */
    Page<UserWalletChargeListDto> list(Pageable<UserWalletChargeOrderQueryDto> pageable);

    /**
     * 押金列表
     * @param pageable
     * @return
     */
    Page<DepositListDto> listDeposit(Pageable<DepositQueryDto> pageable);

    /**
     * 押金详情
     * @param chargeOrderNo
     * @return
     */
    DepositListDto depositDetail(String chargeOrderNo);

    /**
     * 退款页面
     * @param chargeOrderNo
     * @return
     */
    RefundDto refundInfo(String chargeOrderNo);

    /**
     * 退款
     * @param chargeOrderNo
     * @return
     */
    void refund(String chargeOrderNo);

    /**
     * 根据用户id 搜索代充值订单
     * @param userId
     * @return
     */
    UserWalletChargeOrder searchUserWalletChargeOrder(String userId);



    /**
     * 获取代充卡券到期时间
     * @param userId   用户id
     * @param isCreate 是否是线下代充
     * @return
     */
    Date findCardEndTimeByOpenid(String userId,boolean isCreate);
}
