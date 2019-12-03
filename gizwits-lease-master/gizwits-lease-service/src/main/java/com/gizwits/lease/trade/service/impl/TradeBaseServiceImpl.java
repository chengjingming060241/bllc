package com.gizwits.lease.trade.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.app.utils.LeaseUtil;
import com.gizwits.lease.constant.TradeOrderType;
import com.gizwits.lease.constant.TradeStatus;
import com.gizwits.lease.constant.TradeStatusMap;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.order.dao.OrderBaseDao;
import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.refund.dao.RefundBaseDao;
import com.gizwits.lease.refund.entity.RefundBase;
import com.gizwits.lease.trade.dao.TradeBaseDao;
import com.gizwits.lease.trade.entity.TradeBase;
import com.gizwits.lease.trade.service.TradeBaseService;

import com.gizwits.lease.trade.service.TradeWeixinService;
import com.gizwits.lease.util.CommonUtil;
import com.gizwits.lease.util.OrderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author gagi
 * @since 2017-07-29
 */
@Service
public class TradeBaseServiceImpl extends ServiceImpl<TradeBaseDao, TradeBase> implements TradeBaseService {
    private static final Logger logger = LoggerFactory.getLogger(TradeBaseServiceImpl.class);

    @Autowired
    private OrderBaseDao orderBaseDao;

    @Autowired
    private TradeBaseDao tradeBaseDao;

    @Autowired
    private RefundBaseDao refundBaseDao;

    @Autowired
    private TradeWeixinService tradeWeixinService;

    public TradeBase createTradeBase(String orderNo, Double fee, String notifyUrl,Integer tradeType, Integer orderType) {
        //创建tradeBase和tradeWeixin
        TradeBase tradeBase = new TradeBase();
        tradeBase.setTradeNo(LeaseUtil.generateTradeNo(orderNo));
        tradeBase.setCtime(new Date());
        tradeBase.setOrderNo(orderNo);
        tradeBase.setOrderType(orderType);
        tradeBase.setNotifyUrl(notifyUrl);
        tradeBase.setTradeType(tradeType);
        //将交易单设置为创建状态
        tradeBase.setStatus(TradeStatus.INIT.getCode());
        //将订单中的金额转成以分为单位
        tradeBase.setTotalFee(fee);
        tradeBaseDao.insert(tradeBase);
        return tradeBase;
    }


    public TradeBase createTrade(String orderNo, Double fee, String notifyUrl, Integer tradeType, Integer orderType) {
        //查询是否存在tradeBase
        TradeBase tradeBase = selectOne(new EntityWrapper<TradeBase>().eq("order_no", orderNo));
        Date now = new Date();
        if (Objects.isNull(tradeBase)) {
            //创建tradeBase
            return createTradeBase(orderNo, fee, notifyUrl, tradeType, orderType);
        }else {
            // 当再次对同一个订单进行支付时需要创建新的交易单,同时取消原来的交易单
            tradeBase.setStatus(TradeStatus.EXPIRED.getCode());
            tradeBase.setUtime(now);
            updateById(tradeBase);
            return createTradeBase(orderNo, fee, notifyUrl, tradeType, orderType);
        }

    }

    @Override
    public Boolean updateTradeStatus(TradeBase tradeBase, Integer toStatus) {
        if (Objects.isNull(tradeBase) || ParamUtil.isNullOrZero(toStatus)) {
            LeaseException.throwSystemException(LeaseExceEnums.TRADE_NOT_EXIST);
        }
        TradeStatusMap tradeStatusMap = new TradeStatusMap();
        List<Integer> statusList = tradeStatusMap.get(tradeBase.getStatus());
        for (Integer status : statusList) {
            if (toStatus.equals(status)) {
                tradeBase.setStatus(toStatus);
                updateById(tradeBase);
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean updateTradeStatus(String tradeNo, Integer toStatus) {
        if (StringUtils.isEmpty(tradeNo)) {
            LeaseException.throwSystemException(LeaseExceEnums.TRADE_NOT_EXIST);
        }
        //根据tradeNo获取tradeBase
        TradeBase tradeBase = selectById(tradeNo);
        //调用自己的方法
        return updateTradeStatus(tradeBase, toStatus);
    }

    @Override
    public TradeBase selectByTradeNo(String tradeNo) {
        if(ParamUtil.isNullOrEmptyOrZero(tradeNo)){
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }
        TradeBase tradeBase = selectOne(new EntityWrapper<TradeBase>().eq("trade_no",tradeNo));
        return tradeBase;
    }

    @Override
    @Transactional
    public void refund(OrderBase orderBase, BigDecimal refundMoney, Integer refundVersion) {
        if (refundMoney != null) {
            refundMoney = CommonUtil.round(refundMoney);
        }
        logger.info("[refund].orderNo={},amount={},refundMoney={},refund_version={}", orderBase.getOrderNo(), orderBase.getAmount(), refundMoney, refundVersion);

        if (!OrderUtil.isPayByWeixin(orderBase)) {
            // 只支持微信付款的退款
            LeaseException.throwSystemException(LeaseExceEnums.REFUND_METHOD_NOT_SUPPORT);
        }

        TradeBase tradeBase = selectOne(new EntityWrapper<TradeBase>()
                .eq("order_type", TradeOrderType.CONSUME.getCode())
                .eq("order_no", orderBase.getOrderNo()));
        if (Objects.isNull(tradeBase)) {
            LeaseException.throwSystemException(LeaseExceEnums.TRADE_NOT_EXIST);
        }

        // 减少金额
        int rows = orderBaseDao.addRefundMoney(orderBase.getOrderNo(), refundMoney, refundVersion);

        if (rows <= 0) {
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_REFUND_FAIL);
        }

        // 此处有风险，如果发送给微信的退款过程中网络问题，会造成微信中退款成功而本系统中退款失败
        // 执行微信退款
        tradeWeixinService.refund(orderBase, tradeBase);

        // 添加退款记录
        RefundBase refundBase = OrderUtil.createRefundBase(orderBase, tradeBase, refundMoney);
        refundBaseDao.insert(refundBase);
    }
}
