package com.gizwits.lease.refund.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.lease.constant.OrderStatus;
import com.gizwits.lease.constant.PayType;
import com.gizwits.lease.constant.TradeStatus;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.order.service.OrderBaseService;
import com.gizwits.lease.refund.entity.RefundBase;
import com.gizwits.lease.refund.dao.RefundBaseDao;
import com.gizwits.lease.refund.service.RefundBaseService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.lease.trade.entity.TradeBase;
import com.gizwits.lease.trade.service.TradeAlipayService;
import com.gizwits.lease.trade.service.TradeBaseService;
import com.gizwits.lease.trade.service.TradeWeixinService;
import com.gizwits.lease.user.service.UserChargeCardService;
import com.gizwits.lease.wallet.service.UserWalletService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yqli
 * @since 2017-10-26
 */
@Service
public class RefundBaseServiceImpl extends ServiceImpl<RefundBaseDao, RefundBase> implements RefundBaseService {

}
