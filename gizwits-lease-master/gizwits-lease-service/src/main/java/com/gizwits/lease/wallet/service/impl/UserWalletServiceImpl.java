package com.gizwits.lease.wallet.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.constant.*;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.operator.service.OperatorExtService;
import com.gizwits.lease.order.dao.OrderBaseDao;
import com.gizwits.lease.order.entity.OrderBase;
import com.gizwits.lease.order.service.OrderBaseService;
import com.gizwits.lease.trade.entity.TradeBase;
import com.gizwits.lease.trade.service.TradeBaseService;
import com.gizwits.lease.trade.service.TradeWeixinService;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.service.UserService;
import com.gizwits.lease.wallet.dao.UserWalletDao;
import com.gizwits.lease.wallet.dto.RechargeDto;
import com.gizwits.lease.wallet.dto.UserWalletDto;
import com.gizwits.lease.wallet.dto.WalletPayDto;
import com.gizwits.lease.wallet.entity.UserWallet;
import com.gizwits.lease.wallet.entity.UserWalletChargeOrder;
import com.gizwits.lease.wallet.service.RechargeMoneyService;
import com.gizwits.lease.wallet.service.UserWalletChargeOrderService;
import com.gizwits.lease.wallet.service.UserWalletService;
import com.gizwits.lease.wallet.service.UserWalletUseRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户钱包表 服务实现类
 * </p>
 *
 * @author yinhui
 * @since 2017-07-28
 */
@Service
public class UserWalletServiceImpl extends ServiceImpl<UserWalletDao, UserWallet> implements UserWalletService {

    protected final static Logger logger = LoggerFactory.getLogger("PAY_LOGGER");
    @Autowired
    private UserService userService;

    @Autowired
    private UserWalletUseRecordService userWalletUseRecordService;

    @Autowired
    private OrderBaseService orderBaseService;


    @Autowired
    private RechargeMoneyService rechargeMoneyService;
    @Autowired
    private TradeBaseService tradeBaseService;
    @Autowired
    private UserWalletChargeOrderService userWalletChargeOrderService;
    @Autowired
    private TradeWeixinService tradeWeixinService;
    @Autowired
    private DeviceService deviceService;

    public UserWallet createWallet(UserWalletDto userWalletDto) {
        String username = userService.getUserByIdOrOpenidOrMobile(userWalletDto.getMobile()).getUsername();
        UserWallet userWallet = new UserWallet();
        userWallet.setCtime(new Date());
        userWallet.setUsername(username);
        userWallet.setWalletType(userWalletDto.getWalletType());
        String wallet = WalletEnum.getWalletEnumm(userWalletDto.getWalletType()).getName();
        userWallet.setWalletName(wallet);
        userWallet.setMoney(userWalletDto.getFee());
        insert(userWallet);
        return userWallet;
    }

    /**
     * 页面查询余额(余额+赠送金额）
     */
    @Override
    public UserWallet selectUserWallet(UserWalletDto userWalletDto) {
        if(ParamUtil.isNullOrEmptyOrZero(userWalletDto)){
            return  null ;
        }
        User user = userService.getCurrentUser();
        logger.info("查询余额时用户为:"+user);
        if (ParamUtil.isNullOrEmptyOrZero(user)) {
            LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
        }
        logger.info("查询钱包：用户id=" + user.getId());
        UserWallet userWallet = selectUserWallet(user.getId(), WalletEnum.BALENCE.getCode());
        UserWallet userWallet1 = selectUserWallet(user.getId(), WalletEnum.DISCOUNT.getCode());
        BigDecimal money = new BigDecimal(userWallet.getMoney() + userWallet1.getMoney());
        money = money.setScale(2, BigDecimal.ROUND_HALF_UP);
        userWallet.setMoney(money.doubleValue());
        logger.info("页面查询余额（余额+赠送）：" +money.doubleValue());
        return userWallet;
    }

    @Override
    public UserWallet selectUserWallet(Integer  userId, Integer walletType) {
        if (Objects.isNull(userId) || Objects.isNull(walletType)) {
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }
        logger.info("查询钱包：用户id=" + userId + "，钱包类型=" + walletType);
        EntityWrapper<UserWallet> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_id", userId).eq("wallet_type", walletType);
        UserWallet userWallet = selectOne(entityWrapper);
        if (Objects.isNull(userWallet)) {
            userWallet = create(userId, walletType);
        }
        return userWallet;
    }

    @Override
    public UserWallet updateMoney(Integer userId, Double fee, Double discountMoney, Integer operatorType, String tradeNo) {
        logger.info("钱包操作：类型=" + operatorType + ",金额=" + fee + "，赠送金额=" + discountMoney + "，用户id=" + userId);
        logger.info("交易号=" + tradeNo);
        TradeBase tradeBase = tradeBaseService.selectByTradeNo(tradeNo);
        if (Objects.isNull(tradeBase)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        UserWalletChargeOrder chargeOrder = userWalletChargeOrderService.selectById(tradeBase.getOrderNo());
        if (Objects.isNull(chargeOrder)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        //押金单
        if (chargeOrder.getWalletType().equals(WalletEnum.DEPOSIT.getCode())) {
            UserWallet userWallet = create(userId, WalletEnum.DEPOSIT.getCode(), chargeOrder.getFee());
            return userWallet;
        }

        //充值或消费单
        UserWallet userWallet = selectUserWallet(userId, WalletEnum.BALENCE.getCode());
        UserWallet userWallet1 = selectUserWallet(userId, WalletEnum.DISCOUNT.getCode());
        boolean flag = false;
        boolean flag1 = false;
        //充值
        if (Objects.equals(UserWalletUseEnum.RECHARGE.getCode(), operatorType)) {
            userWallet.setMoney(userWallet.getMoney() + fee);
            userWallet.setUtime(new Date());
            userWallet1.setMoney(userWallet1.getMoney() + discountMoney);
            userWallet1.setUtime(new Date());
            flag = userWalletUseRecordService.insertUserWalletUseRecord(userWallet, fee, operatorType, tradeNo);
            flag1 = userWalletUseRecordService.insertUserWalletUseRecord(userWallet1, fee, operatorType, tradeNo);
            //支付 优先使用优惠金额
        } else if (Objects.equals(UserWalletUseEnum.PAY.getCode(), operatorType)) {
            Double surpls = fee - userWallet1.getMoney();
            if (surpls <= 0) {
                //优惠金额足以支付
                userWallet1.setMoney(userWallet1.getMoney() - fee);
                flag1 = userWalletUseRecordService.insertUserWalletUseRecord(userWallet1, fee, operatorType, tradeNo);
                flag = userWalletUseRecordService.insertUserWalletUseRecord(userWallet, 0.0, operatorType, tradeNo);
            } else {
                //扣除优惠金额 一部分余额
                Double momey = userWallet1.getMoney();
                userWallet1.setMoney(0.0);
                flag1 = userWalletUseRecordService.insertUserWalletUseRecord(userWallet1, momey, operatorType, tradeNo);
                userWallet.setMoney(userWallet.getMoney() - surpls);
                flag = userWalletUseRecordService.insertUserWalletUseRecord(userWallet, surpls, operatorType, tradeNo);
            }
        }
        if (updateById(userWallet) && updateById(userWallet1) && flag && flag1) {
            userWallet.setMoney(userWallet.getMoney() + userWallet.getMoney());
            return userWallet;
        } else {
            LeaseException.throwSystemException(LeaseExceEnums.WALLET_OPERATE_FAIL);
        }


        return userWallet;
    }

    @Override
    public UserWallet create(Integer userId, Integer walletType) {
        UserWallet userWallet = new UserWallet();
        userWallet.setCtime(new Date());
        userWallet.setUtime(new Date());
        User user = userService.getUserByIdOrOpenidOrMobile(userId+"");
        if (!ParamUtil.isNullOrEmptyOrZero(user)){
            userWallet.setUsername(user.getNickname());
            userWallet.setUserId(userId);
        }

        userWallet.setWalletType(walletType);
        String wallet = WalletEnum.getWalletEnumm(walletType).getName();
        userWallet.setWalletName(wallet);
        userWallet.setMoney(0.00);
        insert(userWallet);
        return userWallet;
    }

    @Override
    public void payForKali(WalletPayDto data) {
        User user = userService.getCurrentUser();
        if (Objects.isNull(user)) {
            LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
        }
       /* //判断该用户是否有使用中的订单
        List<OrderBase> unfinishOrderList = orderBaseService.findByUserIdAndStatus(user.getId(), OrderStatus.USING.getCode());
        if (unfinishOrderList != null && unfinishOrderList.size() > 0) {
            LeaseException.throwSystemException(LeaseExceEnums.HAS_UNFINISH_ORDER);
        }*/

        //根据openid和orderNo获取订单信息
        UserWallet userWallet = selectUserWallet(user.getId(), WalletEnum.BALENCE.getCode());
        UserWallet userWallet1 = selectUserWallet(user.getId(), WalletEnum.DISCOUNT.getCode());
        OrderBase orderBase = orderBaseService.getOrderBaseByOrderNo(data.getOrderNo());
        if (Objects.isNull(orderBase) || orderBase.getIsDeleted().equals(1)) {
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_DONT_EXISTS);
        }
        //1.钱包里面的钱要比订单的钱要多才能支付
        if (orderBase.getAmount().compareTo(userWallet.getMoney() + userWallet1.getMoney()) > 0) {
            LeaseException.throwSystemException(LeaseExceEnums.BALANCE_NOT_ENOUGH);
        }
        //判断设备是否可用租赁
        deviceService.checkDeviceIsRenting(orderBase.getSno(),false);

        //兼容支付中的订单
        if (orderBase.getOrderStatus().equals(OrderStatus.PAYING.getCode())) {
            orderBase.setOrderStatus(OrderStatus.INIT.getCode());
        }
        //创建tradeBase
        TradeBase tradeBase = tradeBaseService.createTrade(orderBase.getOrderNo(), orderBase.getAmount(), "", PayType.BALANCE.getCode(), TradeOrderType.CONSUME.getCode());
        //先将订单状态更改为支付中
        orderBaseService.updateOrderStatusAndHandle(orderBase, OrderStatus.PAYING.getCode());
        //支付金额
        updateMoneyForKali(user.getId(), orderBase.getAmount(), orderBase.getPromotionMoney(), UserWalletUseEnum.PAY.getCode(), tradeBase.getTradeNo());
        orderBase.setPayTime(new Date());
        orderBase.setPayType(PayType.BALANCE.getCode());
        //2.更新订单状态
        orderBaseService.updateOrderStatusAndHandle(orderBase, OrderStatus.PAYED.getCode());
    }

    private UserWallet updateMoneyForKali(Integer userId, Double fee, Double discountMoney, Integer operatorType, String tradeNo) {
        logger.info("钱包操作：类型=" + operatorType + ",金额=" + fee + "，赠送金额=" + discountMoney + "，用户id=" + userId);
        logger.info("交易号=" + tradeNo);
        UserWallet userWallet = selectUserWallet(userId, WalletEnum.BALENCE.getCode());
        UserWallet userWallet1 = selectUserWallet(userId, WalletEnum.DISCOUNT.getCode());
        boolean flag = false;
        boolean flag1 = false;
        //充值
        if (Objects.equals(UserWalletUseEnum.RECHARGE.getCode(), operatorType)) {
            userWallet.setMoney(userWallet.getMoney() + fee);
            userWallet.setUtime(new Date());
            userWallet1.setMoney(userWallet1.getMoney() + discountMoney);
            userWallet1.setUtime(new Date());
            flag = userWalletUseRecordService.insertUserWalletUseRecord(userWallet, fee, operatorType, tradeNo);
            flag1 = userWalletUseRecordService.insertUserWalletUseRecord(userWallet1, fee, operatorType, tradeNo);
            //支付 优先使用优惠金额
        } else if (Objects.equals(UserWalletUseEnum.PAY.getCode(), operatorType)) {
            Double surpls = fee - userWallet1.getMoney();
            if (surpls <= 0) {
                //优惠金额足以支付
                userWallet1.setMoney(userWallet1.getMoney() - fee);
                flag1 = userWalletUseRecordService.insertUserWalletUseRecord(userWallet1, fee, operatorType, tradeNo);
                flag = userWalletUseRecordService.insertUserWalletUseRecord(userWallet, 0.0, operatorType, tradeNo);
            } else {
                //扣除优惠金额 一部分余额
                Double momey = userWallet1.getMoney();
                userWallet1.setMoney(0.0);
                flag1 = userWalletUseRecordService.insertUserWalletUseRecord(userWallet1, momey, operatorType, tradeNo);
                userWallet.setMoney(userWallet.getMoney() - surpls);
                flag = userWalletUseRecordService.insertUserWalletUseRecord(userWallet, surpls, operatorType, tradeNo);
            }
        }
        if (updateById(userWallet) && updateById(userWallet1) && flag && flag1) {
            userWallet.setMoney(userWallet.getMoney() + userWallet.getMoney());
            return userWallet;
        } else {
            LeaseException.throwSystemException(LeaseExceEnums.WALLET_OPERATE_FAIL);
        }


        return userWallet;
    }

    private UserWallet create(Integer userId, Integer walletType, Double money) {
        UserWallet userWallet = create(userId, walletType);
        userWallet.setMoney(money);
        updateById(userWallet);
        return userWallet;
    }

    @Override
    @Transactional
    public void pay(WalletPayDto data) {
        User user = userService.getCurrentUser();
        if (Objects.isNull(user)) {
            LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
        }
       /* //判断该用户是否有使用中的订单
        List<OrderBase> unfinishOrderList = orderBaseService.findByUserIdAndStatus(user.getId(), OrderStatus.USING.getCode());
        if (unfinishOrderList != null && unfinishOrderList.size() > 0) {
            LeaseException.throwSystemException(LeaseExceEnums.HAS_UNFINISH_ORDER);
        }
*/
        //根据openid和orderNo获取订单信息
        UserWallet userWallet = selectUserWallet(user.getId(), WalletEnum.BALENCE.getCode());
        UserWallet userWallet1 = selectUserWallet(user.getId(), WalletEnum.DISCOUNT.getCode());
        OrderBase orderBase = orderBaseService.getOrderBaseByOrderNo(data.getOrderNo());
        if (Objects.isNull(orderBase) || orderBase.getIsDeleted().equals(1)) {
            LeaseException.throwSystemException(LeaseExceEnums.ORDER_DONT_EXISTS);
        }
        //1.钱包里面的钱要比订单的钱要多才能支付
        if (orderBase.getAmount().compareTo(userWallet.getMoney() + userWallet1.getMoney()) > 0) {
            LeaseException.throwSystemException(LeaseExceEnums.BALANCE_NOT_ENOUGH);
        }
        // 获取设备当前订单
        boolean canRenew = false;
        OrderBase oldBase = orderBaseService.getDeviceLastUsingOrder(orderBase.getSno());
        // 如果是本人正在用，允许续费，否则报错设备非空闲
        if (oldBase != null && oldBase.getUserId().equals(user.getId())) {
            canRenew = true;
        }
        //判断设备是否可用租赁
        deviceService.checkDeviceIsRenting(orderBase.getSno(),canRenew);

        //兼容支付中的订单
        if (orderBase.getOrderStatus().equals(OrderStatus.PAYING.getCode())) {
            orderBase.setOrderStatus(OrderStatus.INIT.getCode());
        }
        //创建tradeBase
        TradeBase tradeBase = tradeBaseService.createTrade(orderBase.getOrderNo(), orderBase.getAmount(), "", PayType.BALANCE.getCode(), TradeOrderType.CONSUME.getCode());
        //先将订单状态更改为支付中
        orderBaseService.updateOrderStatusAndHandle(orderBase, OrderStatus.PAYING.getCode());
        //支付金额
        updateMoney(user.getId(), orderBase.getAmount(), orderBase.getPromotionMoney(), UserWalletUseEnum.PAY.getCode(), tradeBase.getTradeNo());
	    //修改交易单状态和回调时间
	    tradeBase.setUtime(new Date());
	    tradeBase.setNofifyTime(new Date());
	    if (!tradeBaseService.updateTradeStatus(tradeBase, TradeStatus.SUCCESS.getCode())) {
		    logger.error("===============>修改交易号:" + tradeBase.getTradeNo() + "的状态失败");
	    }
        //2.更新订单状态
        orderBase.setPayTime(new Date());
        orderBase.setPayType(PayType.BALANCE.getCode());
        orderBaseService.updateOrderStatusAndHandle(orderBase, OrderStatus.PAYED.getCode());
    }


    @Override
    public RechargeDto getRechargeDto(UserWalletDto userWalletDto) {
        RechargeDto rechargeDto = new RechargeDto(selectUserWallet(userWalletDto));
        RechargeDto rechargeDto1 = rechargeMoneyService.getRechargeMoney(userWalletDto.getProjectId());
        rechargeDto.setRechargeMoneyDtos(rechargeDto1.getRechargeMoneyDtos());
        return rechargeDto;
    }

    @Override
    public void refund(OrderBase orderBase, TradeBase tradeBase){
        User user = userService.selectById(orderBase.getUserId());
        if(user == null)LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        UserWallet userWallet = selectUserWallet(user.getId(), WalletEnum.BALENCE.getCode());
        if(userWallet == null)LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        UserWallet forUpdate = new UserWallet();
        forUpdate.setId(userWallet.getId());
        forUpdate.setMoney(userWallet.getMoney()+orderBase.getAmount());
        updateById(forUpdate);
    }

    @Override
    public List<UserWallet> getAllWallet(){
        User user = userService.getCurrentUser();
        List<Integer> walletType = new LinkedList<>();
        walletType.add(WalletEnum.BALENCE.getCode());
        walletType.add(WalletEnum.DEPOSIT.getCode());
        return selectList(new EntityWrapper<UserWallet>().eq("user_id",user.getId()).in("wallet_type",walletType));
    }
}

