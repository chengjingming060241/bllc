package com.gizwits.lease.util;

import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.lease.app.utils.LeaseUtil;
import com.gizwits.lease.constant.OrderStatus;
import com.gizwits.lease.constant.PayType;
import com.gizwits.lease.enums.RefundMethod;
import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.refund.entity.RefundBase;
import com.gizwits.lease.trade.entity.TradeBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 订单工具类
 * Created by yuqing on 2017/10/26.
 */
public class OrderUtil {

    private static final Logger logger = LoggerFactory.getLogger(OrderUtil.class);

    /**
     * 订单是否可以执行退款操作
     * @param orderBase
     * @return
     */
    public static boolean isEarlyEnd(OrderBase orderBase) {
        return Objects.equals(Boolean.TRUE, orderBase.getEarlyEnd());
    }

    /**
     * 计算订单使用的价格
     * @param orderBase
     * @param availableTime
     * @return
     */
    public static BigDecimal calcUsedMoney(OrderBase orderBase, int availableTime) {
        logger.info("orderNo={},availableTime={},serviceStart={},serviceEnd={},amount={}",
                orderBase.getOrderNo(),
                availableTime,
                orderBase.getServiceStartTime(),
                orderBase.getServiceEndTime(),
                orderBase.getAmount());

        if (Objects.isNull(orderBase.getServiceStartTime()) || Objects.isNull(orderBase.getServiceEndTime())) {
            return BigDecimal.ZERO;
        }

        // 时长，单位分
        long duration = (orderBase.getServiceEndTime().getTime() - orderBase.getServiceStartTime().getTime()) / 1000;

        // 转化为分钟，不足一分钟按照一分钟计算
        duration = (duration + 59) / 60;

        if (duration < 0 || duration > availableTime) {
            // 如果时长小于0或者大于预设值都不需要退款
            return BigDecimal.ZERO;
        }

        BigDecimal used = CommonUtil.round((duration * 1.0 / availableTime * orderBase.getAmount()), 2);

        if (logger.isDebugEnabled()) {
            logger.debug("orderNo={},duration={},used={}",
                    orderBase.getOrderNo(), duration, used);
        }

        return used;
    }

    public static Long calcDeviceUseTime(OrderBase orderBase) {
        return calcDeviceUseTime(orderBase.getOrderStatus(), orderBase.getCtime(), orderBase.getUtime());
    }

    public static Long calcDeviceUseTime(Integer orderStatus, Date ctime, Date utime) {
        Long dut = null;
        if (Objects.equals(orderStatus, OrderStatus.FINISH.getCode())) {
            Long time = utime.getTime() - ctime.getTime();
            dut = time;
        }

        if (Objects.equals(orderStatus, OrderStatus.USING.getCode())) {
            utime = new Date();
            Long time = utime.getTime() - ctime.getTime();
            dut = time;
        }
        return dut;
    }

    /**
     * 是否为微信付款
     * @param orderBase
     * @return
     */
    public static boolean isPayByWeixin(OrderBase orderBase) {
        return isPayByWeixin(orderBase.getPayType());
    }

    /**
     * 是否为微信付款
     * @param payType
     * @return
     */
    public static boolean isPayByWeixin(Integer payType) {
        if (payType == null) {
            return false;
        }

        if (PayType.WX_JSAPI.getCode().equals(payType)) {
            return true;
        }

        if (PayType.WX_APP.getCode().equals(payType)) {
            return true;
        }

        if (PayType.WEIXINPAY.getCode().equals(payType)) {
            return true;
        }

        return PayType.WX_H5.getCode().equals(payType);

    }

    /**
     * 创建RefundBase
     * @param orderBase
     * @param tradeBase
     * @param refundMoney
     * @return
     *
     * @see RefundBase
     */
    public static RefundBase createRefundBase(OrderBase orderBase, TradeBase tradeBase, BigDecimal refundMoney) {
        RefundBase refundBase = new RefundBase();
        refundBase.setRefundNo(LeaseUtil.generateRefundNo());
        refundBase.setOrderNo(orderBase.getOrderNo());
        refundBase.setAmount(refundMoney);
        refundBase.setTradeNo(tradeBase.getTradeNo());
        refundBase.setRefundMethod(RefundMethod.WEIXIN.getCode());
        refundBase.setCtime(new Date());
        return refundBase;
    }

    public static boolean isAuthorizedOrder(OrderBase orderBase, SysUserService sysUserService) {
        if (Objects.isNull(orderBase) || Objects.isNull(orderBase.getSysUserId())) {
            return false;
        }
        SysUser sysUser =  sysUserService.getCurrentUser();
        if (Objects.isNull(sysUser)) {
            return false;
        }
        List<Integer> ids = sysUserService.resolveAccessableUserIds(sysUser);

        return ids != null && ids.contains(orderBase.getSysUserId());
    }
}
