package com.gizwits.lease.wallet.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.entity.SysUserExt;
import com.gizwits.boot.sys.service.SysUserExtService;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.QueryResolverUtils;
import com.gizwits.boot.utils.SysConfigUtils;
import com.gizwits.lease.app.utils.LeaseUtil;

import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.constant.*;

import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;

import com.gizwits.lease.order.dto.ChargeOrderDto;
import com.gizwits.lease.order.dto.DepositOrderDto;
import com.gizwits.lease.trade.service.TradeWeixinService;
import com.gizwits.lease.user.dao.UserDao;
import com.gizwits.lease.user.entity.User;
import com.gizwits.lease.user.service.UserService;
import com.gizwits.lease.wallet.dao.UserWalletChargeOrderDao;
import com.gizwits.lease.wallet.dto.DepositListDto;
import com.gizwits.lease.wallet.dto.DepositQueryDto;
import com.gizwits.lease.wallet.dto.RefundDto;
import com.gizwits.lease.wallet.dto.UserWalletChargeListDto;
import com.gizwits.lease.wallet.dto.UserWalletChargeOrderQueryDto;
import com.gizwits.lease.wallet.entity.RechargeMoney;
import com.gizwits.lease.wallet.entity.UserWallet;
import com.gizwits.lease.wallet.entity.UserWalletChargeOrder;
import com.gizwits.lease.wallet.service.RechargeMoneyService;
import com.gizwits.lease.wallet.service.UserWalletChargeOrderService;
import com.gizwits.lease.wallet.service.UserWalletService;

import com.gizwits.lease.winxin.service.WeixinPayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 * 用户钱包充值单表 服务实现类
 * </p>
 *
 * @author yinhui
 * @since 2017-07-31
 */
@Service
public class UserWalletChargeOrderServiceImpl extends ServiceImpl<UserWalletChargeOrderDao, UserWalletChargeOrder> implements UserWalletChargeOrderService {


    private static Logger logger = LoggerFactory.getLogger("ORDER_LOGGER");

    @Autowired
    private UserService userService;
    @Autowired
    private UserWalletService userWalletService;

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserExtService sysUserExtService;

    @Autowired
    private RechargeMoneyService rechargeMoneyService;

    @Autowired
    private UserWalletChargeOrderDao userWalletChargeOrderDao;

    @Autowired
    private TradeWeixinService tradeWeixinService;



    @Autowired
    private UserDao userDao;


    @Autowired
    private WeixinPayService weixinPayService;



    @Override
    public UserWalletChargeOrder createRechargeOrder(ChargeOrderDto chargeOrderDto) {
        User user = userService.getCurrentUser();

        // if (Objects.isNull(user)) {
        //     LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
        // }

        UserWalletChargeOrder userWalletChargeOrder = new UserWalletChargeOrder();
        Double fee = 0.0;
        if (!ParamUtil.isNullOrEmptyOrZero(chargeOrderDto.getFee())) {
            fee = chargeOrderDto.getFee();
        }
        double discountMoney = 0;
        if (ParamUtil.isNullOrEmptyOrZero(fee)) {
        //判断表示rechargeId不为空
        if (ParamUtil.isNullOrEmptyOrZero(chargeOrderDto.getRechargeId())) {
            LeaseException.throwSystemException(LeaseExceEnums.INPUT_FEE);
        }
            RechargeMoney  rechargeMoney = rechargeMoneyService.selectById(chargeOrderDto.getRechargeId());
            fee = rechargeMoney.getChargeMoney();
            discountMoney = rechargeMoney.getDiscountMoney();
        } else {
            BigDecimal r = rechargeMoneyService.getRate(user.getSysUserId());
            if (!Objects.isNull(r)) {
                discountMoney = new BigDecimal(fee).multiply(r).doubleValue();
            }
        }
        logger.info("创建充值单：fee=" + fee + ",rechargeId=" + chargeOrderDto.getRechargeId());
        userWalletChargeOrder.setFee(fee);
        userWalletChargeOrder.setChargeOrderNo(LeaseUtil.generateOrderNo(TradeOrderType.CHARGE.getCode()));
        userWalletChargeOrder.setWalletType(WalletEnum.BALENCE.getCode());
        userWalletChargeOrder.setWalletName(WalletEnum.BALENCE.getName());
        userWalletChargeOrder.setCtime(new Date());
        userWalletChargeOrder.setDiscountMoney(discountMoney);
        //余额
        UserWallet balance = userWalletService.selectUserWallet(user.getId(),WalletEnum.BALENCE.getCode());
        userWalletChargeOrder.setBalance(balance.getMoney() + fee);
        userWalletChargeOrder.setUsername(user.getNickname());
        userWalletChargeOrder.setUserId(user.getId());
        userWalletChargeOrder.setStatus(UserWalletChargeOrderType.INIT.getCode());
        insert(userWalletChargeOrder);
        return userWalletChargeOrder;
    }






    @Override
    public void updateCardTicketStatus(UserWalletChargeOrder userWalletChargeOrder, Integer toStatus) {
        if (Objects.isNull(userWalletChargeOrder) || Objects.isNull(toStatus)) {
            LeaseException.throwSystemException(LeaseExceEnums.CHARGE_ORDER_NOT_EXIST);
        }
        Boolean flag = false;
        UserChargeStatusMap map = new UserChargeStatusMap();
        List<Integer> statusList = map.get(userWalletChargeOrder.getStatus());
        for (Integer status : statusList) {
            if (status.equals(toStatus)) {
                userWalletChargeOrder.setStatus(toStatus);
                updateById(userWalletChargeOrder);
                flag = true;
                break;
            }
        }
        if (!flag) {
            LeaseException.throwSystemException(LeaseExceEnums.CHARGE_ORDER_CHANGE_STATUS_FAIL);
        }
    }

    /**
     * 获取代充卡券到期时间
     * @param userId
     * @return
     */
    @Override
    public Date findCardEndTimeByOpenid(String userId,boolean isCreate){
        Date maxEndTime = userDao.findMaxEndTimeByOpenid(userId);
        Date now = new Date(); //当前时间
        //线下代充
        /**
         * 假如查询的到期时间小于当前时间，在当前时间上加一年
         * 假如查询的到期时间大于当前时间，在查询日期时间上加一年
         */
        if (maxEndTime == null){
            if (isCreate){
                return  getSpecifiedYearAfter(now);
            }else {
                return  now;
            }

        }else {
            if (isCreate){
                return getSpecifiedYearAfter(maxEndTime);

            }else {
                return  maxEndTime;
            }
        }
    }

    /**
     * 获得指定日期的后一年
     * @param currentTime
     * @return
     */
    static  Date getSpecifiedYearAfter(Date currentTime){
        Calendar c = Calendar.getInstance();
        Date date= new Date(currentTime.getTime());
        c.setTime(date);
        c.add(Calendar.YEAR, +1);
        return c.getTime();
    }




    public UserWalletChargeOrder createDepositOrder(DepositOrderDto depositOrderDto) {
        User user = userService.getCurrentUser();
//        // if (Objects.isNull(user)) {
//        //     LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
//        // }
//        Integer operatorSysuserid = deviceService.getDeviceOperatorSysuserid(depositOrderDto.getSno());
//        if (operatorSysuserid == null) {
//            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_NOT_IN_OPERATOR);
//        }
//        Operator operator = operatorService.getOperatorByAccountId(operatorSysuserid);
//        if (operator == null) {
//            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
//        }
//        OperatorExt operatorExt = operatorExtService.selectOne((new EntityWrapper<OperatorExt>().eq("operator_id", operator.getId())));
////        if(operatorExt==null){
////            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
////        }
//
//        BigDecimal fee = BigDecimal.ZERO;
//        if (operatorExt != null) {
//            fee = operatorExt.getCashPledge();
//        }
        BigDecimal fee = new BigDecimal(SysConfigUtils.get(CommonSystemConfig.class).getDeposit());

        UserWalletChargeOrder userWalletChargeOrder = new UserWalletChargeOrder();
        userWalletChargeOrder.setChargeOrderNo(LeaseUtil.generateOrderNo(TradeOrderType.CHARGE.getCode()));
        userWalletChargeOrder.setWalletType(WalletEnum.DEPOSIT.getCode());
        userWalletChargeOrder.setCtime(new Date());
        userWalletChargeOrder.setFee(fee.doubleValue());
        userWalletChargeOrder.setUsername(user.getUsername());
        userWalletChargeOrder.setStatus(UserWalletChargeOrderType.INIT.getCode());
        insert(userWalletChargeOrder);

        return userWalletChargeOrder;
    }

    @Override
    public void updateChargeOrderStatus(String chargeOrderNo, Integer toStatus) {
        if (StringUtils.isEmpty(chargeOrderNo)) {
            LeaseException.throwSystemException(LeaseExceEnums.CHARGE_ORDER_NOT_EXIST);
        }
        UserWalletChargeOrder chargeOrder = selectById(chargeOrderNo);
        updateChargeOrderStatus(chargeOrder, toStatus);
    }

    @Override
    public void updateChargeOrderStatus(UserWalletChargeOrder userWalletChargeOrder, Integer toStatus) {
        if (Objects.isNull(userWalletChargeOrder) || Objects.isNull(toStatus)) {
            LeaseException.throwSystemException(LeaseExceEnums.CHARGE_ORDER_NOT_EXIST);
        }
        Boolean flag = false;
//        UserChargeStatusMap map = new UserChargeStatusMap();
//        List<Integer> statusList = map.get(userWalletChargeOrder.getStatus());
//        for (Integer status : statusList) {
//            if (status.equals(toStatus)) {
//                userWalletChargeOrder.setStatus(toStatus);
//                updateById(userWalletChargeOrder);
//                flag = true;
//                break;
//            }
//        }
        userWalletChargeOrder.setStatus(toStatus);
        flag = updateById(userWalletChargeOrder);

        if (!flag) {
            LeaseException.throwSystemException(LeaseExceEnums.CHARGE_ORDER_CHANGE_STATUS_FAIL);
        }
    }

    @Override
    public Page<UserWalletChargeListDto> list(Pageable<UserWalletChargeOrderQueryDto> pageable) {

        SysUser sysUser = sysUserService.getCurrentUserOwner();
        UserWalletChargeOrderQueryDto queryDto = pageable.getQuery();
        if (Objects.isNull(queryDto)) {
            queryDto = new UserWalletChargeOrderQueryDto();
        }
        List<Integer> sysUserIds = sysUserService.resolveSysUserAllSubIds(sysUser);
        List<Integer> userList =
                userService.selectList(new EntityWrapper<User>().in("sys_user_id", sysUserIds))
                        .stream().map(User::getId).collect(Collectors.toList());

        queryDto.setUserIds(userList);

        Page<UserWalletChargeOrder> page = new Page<>();
        BeanUtils.copyProperties(pageable, page);
        Page<UserWalletChargeListDto> result = new Page<>();
        Page<UserWalletChargeOrder> page1 = selectPage(page,
                QueryResolverUtils.parse(queryDto, new EntityWrapper<>()));
        List<UserWalletChargeOrder> chargeOrders = page1.getRecords();
        List<UserWalletChargeListDto> listDtos = new ArrayList<>(chargeOrders.size());
        for (UserWalletChargeOrder chargeOrder : chargeOrders) {
            UserWalletChargeListDto listDto = new UserWalletChargeListDto();
            listDto.setChargeNo(chargeOrder.getChargeOrderNo());
            listDto.setFee(chargeOrder.getFee());
            listDto.setPayTime(chargeOrder.getPayTime());
            listDto.setPayType(PayType.getName(chargeOrder.getPayType()));
            listDto.setStatus(UserWalletChargeOrderType.get(chargeOrder.getStatus()).getName());
            listDto.setNickName(chargeOrder.getUsername());
            listDto.setUserId(chargeOrder.getUserId());
            listDto.setCardType(CardType.getName(chargeOrder.getCardType()));
            listDto.setConcession(chargeOrder.isConcession());
            listDto.setUsableTimes(chargeOrder.getUsableTimes());
            listDto.setDefaultUsage(chargeOrder.getDefaultUsage());
            listDtos.add(listDto);
        }

        BeanUtils.copyProperties(page1, result);
        result.setRecords(listDtos);
        return result;
    }


    @Override
    public Page<DepositListDto> listDeposit(Pageable<DepositQueryDto> pageable) {
        SysUser sysUser = sysUserService.getCurrentUserOwner();
        List<Integer> ids = userService.selectList(new EntityWrapper<User>().in("sys_user_id", sysUserService.resolveSysUserAllSubAdminIds(sysUser)))
                .stream().map(User::getId).collect(Collectors.toList());
        DepositQueryDto queryDto = pageable.getQuery();
        if (queryDto == null) {
            queryDto = new DepositQueryDto();
        }
        if (!ParamUtil.isNullOrEmptyOrZero(ids)) {
            queryDto.setUserIds(ids);
        }
        //钱包类型为押金
        queryDto.setWalletType(WalletEnum.DEPOSIT.getCode());
        queryDto.setStatus(OrderStatus.FINISH.getCode());
        Page<UserWalletChargeOrder> page = new Page<>();
        BeanUtils.copyProperties(pageable, page);
        Page<UserWalletChargeOrder> page1 = selectPage(page,
                QueryResolverUtils.parse(pageable.getQuery(), new EntityWrapper<UserWalletChargeOrder>()));

        Page<DepositListDto> result = new Page<>();
        Integer pagesize = pageable.getSize();
        Integer current = pageable.getCurrent();
        Integer start = (current - 1) * pagesize;
        queryDto.setPagesize(pagesize);
        queryDto.setStart(start);
        List<DepositListDto> listDtos = userWalletChargeOrderDao.listDeposit(queryDto);
        for (DepositListDto listDto : listDtos) {
            if (!ParamUtil.isNullOrEmptyOrZero(listDto.getPayType())) {
                listDto.setPayType(PayType.getName(Integer.parseInt(listDto.getPayType())));
            }

        }
        BeanUtils.copyProperties(page1, result);
        result.setRecords(listDtos);
        result.setTotal(userWalletChargeOrderDao.countNum(queryDto));
        return result;
    }

    @Override
    public DepositListDto depositDetail(String chargeOrderNo) {
        DepositListDto depositListDto = new DepositListDto();
        UserWalletChargeOrder chargeOrder = selectById(chargeOrderNo);
        if (chargeOrder == null) {
            LeaseException.throwSystemException(LeaseExceEnums.CHARGE_ORDER_NOT_EXIST);
        }
        depositListDto.setChargeOrderNo(chargeOrder.getChargeOrderNo());
        depositListDto.setMoney(chargeOrder.getFee());
        depositListDto.setPayTime(chargeOrder.getPayTime());
        if (ParamUtil.isNullOrEmptyOrZero(chargeOrder.getPayType())) {
            depositListDto.setPayType(PayType.getName(chargeOrder.getPayType()));
        }
        User user = userService.getUserByUsername(chargeOrder.getUsername());
        if (user == null) {
            LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
        }

        depositListDto.setMobile(user.getMobile());
        depositListDto.setNickName(user.getNickname());
        return depositListDto;
    }

    @Override
    public RefundDto refundInfo(String chargeOrderNo) {
        RefundDto refundDto = new RefundDto();
        UserWalletChargeOrder chargeOrder = selectById(chargeOrderNo);
        if (Objects.isNull(chargeOrder)) {
            LeaseException.throwSystemException(LeaseExceEnums.CHARGE_ORDER_NOT_EXIST);
        }
        User user = userService.getUserByIdOrOpenidOrMobile(chargeOrder.getUserId()+"");
        SysUserExt sysUserExt = sysUserExtService.selectOne(new EntityWrapper<SysUserExt>().eq("wx_id", user.getWxId()));

        refundDto.setUsername(user.getNickname());
        BigDecimal money = new BigDecimal(chargeOrder.getFee()).setScale(2, BigDecimal.ROUND_HALF_UP);
        refundDto.setMoney(money);
        refundDto.setOrderNo(chargeOrderNo);
        refundDto.setPayAccount(sysUserExt.getWxParenterId());
        return refundDto;
    }

    /**
     * 押金单退款
     * @param chargeOrderNo
     */
    @Override
    public void refund(String chargeOrderNo) {
        if (ParamUtil.isNullOrEmptyOrZero(chargeOrderNo)) {
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }

        logger.info("====>>> 订单:{},执行退款操作", chargeOrderNo);
        //目前只有微信退款
       tradeWeixinService.handleChargeRefund(chargeOrderNo);
    }

    @Override
    public UserWalletChargeOrder searchUserWalletChargeOrder(String userId) {

        return selectOne((new EntityWrapper<UserWalletChargeOrder>()
                .eq("sys_user_id", userId)
                .eq("status", "1")));
    }


    @Transactional
    public Boolean checkAndUpdateChargeOrder(Double totalFee, String orderNo, Integer payType, String tradeNo) {
        logger.info("充值押金单 处理 chargeOrderNo : []",orderNo);
        if (ParamUtil.isNullOrEmptyOrZero(totalFee) ||
                ParamUtil.isNullOrEmptyOrZero(orderNo) ||
                ParamUtil.isNullOrEmptyOrZero(tradeNo) ||
                ParamUtil.isNullOrEmptyOrZero(payType)) {
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }

        UserWalletChargeOrder userWalletOrder = selectById(orderNo);
        if (userWalletOrder == null) {
            logger.error("====>>>>> 订单orderNo[" + userWalletOrder.getChargeOrderNo() + "]在系统中未找到");
            return false;
        }

        //检查订单的状态是否是未支付
        if (userWalletOrder.getPayTime() != null || UserWalletChargeOrderType.PAYED.getCode().equals(userWalletOrder.getStatus())) {
            logger.warn("====>>>> 订单tradeNo[" + tradeNo + "]的状态为已支付,本次支付回调不做处理");
            return false;
        }

        //检查订单金额是否一致,注:微信回调中的金额单位是分,需要转换为元
        if (!totalFee.equals(userWalletOrder.getFee())) {
            logger.error("====>>>>> 订单tradeNo[" + tradeNo + "]的金额为[" + userWalletOrder.getFee() + "]与支付回调的金额[" + totalFee + "]的金额不匹配,本次支付回调不做处理");
            return false;
        }

        Date now = new Date();
        userWalletOrder.setPayTime(now);
        userWalletOrder.setPayType(payType);
        //修改订单状态和修改订单支付时间
        updateChargeOrderStatus(userWalletOrder, UserWalletChargeOrderType.PAYED.getCode());
        //充值
        userWalletService.updateMoney(userWalletOrder.getUserId(),
                userWalletOrder.getFee(),
                userWalletOrder.getDiscountMoney(),
                UserWalletUseEnum.RECHARGE.getCode(), tradeNo);

        return true;
    }


}
