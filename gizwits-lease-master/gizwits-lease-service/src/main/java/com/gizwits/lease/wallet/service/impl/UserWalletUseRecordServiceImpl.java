package com.gizwits.lease.wallet.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.QueryResolverUtils;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.user.entity.User;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.SysConfigUtils;
import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.user.service.UserService;
import com.gizwits.lease.constant.UserWalletUseEnum;
import com.gizwits.lease.constant.WalletEnum;
import com.gizwits.lease.enums.ChargeMoneyEnum;
import com.gizwits.lease.wallet.dao.UserWalletUseRecordDao;
import com.gizwits.lease.wallet.dto.UserWalletUseDto;
import com.gizwits.lease.wallet.dto.UserWalletUseRecordDto;
import com.gizwits.lease.wallet.entity.UserWallet;
import com.gizwits.lease.wallet.entity.UserWalletUseRecord;
import com.gizwits.lease.wallet.service.UserWalletService;
import com.gizwits.lease.wallet.service.UserWalletUseRecordService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 用户钱包操作记录表 服务实现类
 * </p>
 *
 * @author yinhui
 * @since 2017-07-28
 */
@Service
public class UserWalletUseRecordServiceImpl extends ServiceImpl<UserWalletUseRecordDao, UserWalletUseRecord> implements UserWalletUseRecordService {
    protected final static Logger logger = LoggerFactory.getLogger("PAY_LOGGER");

    @Autowired
    private UserService userService;
    @Autowired
    private UserWalletUseRecordDao userWalletUseRecordDao;

    @Override
    public boolean insertUserWalletUseRecord(UserWallet userWallet, Double fee, Integer opeartorType, String tradeNo) {
        logger.info("添加钱包使用记录：用户id="+userWallet.getId()+"，金额="+fee+"，操作类型="+opeartorType+"，交易号="+tradeNo);
        UserWalletUseRecord userWalletUseRecord = new UserWalletUseRecord();
        userWalletUseRecord.setCtime(new Date());
        userWalletUseRecord.setUtime(new Date());
        userWalletUseRecord.setUsername(userWallet.getUsername());
        userWalletUseRecord.setUserId(userWallet.getUserId());
        userWalletUseRecord.setWalletType(userWallet.getWalletType());
        userWalletUseRecord.setWalletName(userWallet.getWalletName());
        userWalletUseRecord.setOperationType(opeartorType);
        userWalletUseRecord.setTradeNo(tradeNo);
        userWalletUseRecord.setFee(fee);
        userWalletUseRecord.setBalance(userWallet.getMoney());
        return insert(userWalletUseRecord);
    }

    @Override
    public Page<UserWalletUseRecordDto> listPage(Pageable<String> pageable, Integer operatorType) {
        User user = userService.getUserByIdOrOpenidOrMobile(pageable.getQuery());
        if (ParamUtil.isNullOrEmptyOrZero(user)) {
            LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
        }
        Integer userId = user.getId();
        logger.info("查询用户的钱包充值记录：userId="+userId);
        EntityWrapper<UserWalletUseRecord> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_id", user.getId()).eq("operation_type", operatorType);
        Page<UserWalletUseRecord> page = new Page<>();
        BeanUtils.copyProperties(pageable, page);
        Page<UserWalletUseRecord> page1 = selectPage(page, QueryResolverUtils.parse(pageable.getQuery(), entityWrapper));
        Page<UserWalletUseRecordDto> result = new Page<>();
        BeanUtils.copyProperties(page1, result);
        Integer current = page1.getCurrent();
        Integer pagesize = page1.getSize();
        Integer begin = (current - 1) * pagesize;
        result.setRecords(listUserWalletUseRecord(userId, operatorType, pagesize, begin));
        result.setTotal(countNum(userId,operatorType));
        return result;
    }

    @Override
    public Page<UserWalletUseRecordDto> listPage(Pageable pageable) {
        User user = userService.getCurrentUser();
        if (ParamUtil.isNullOrEmptyOrZero(user)) {
            LeaseException.throwSystemException(LeaseExceEnums.USER_DONT_EXISTS);
        }
        Integer userId = user.getId();
        logger.info("查询用户的钱包使用记录：userId = "+userId);
        EntityWrapper<UserWalletUseRecord> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("user_id", userId);
        Page<UserWalletUseRecord> page = new Page<>();
        BeanUtils.copyProperties(pageable, page);
        Page<UserWalletUseRecord> page1 = selectPage(page, QueryResolverUtils.parse(pageable.getQuery(), entityWrapper));
        Page<UserWalletUseRecordDto> result = new Page<>();
        BeanUtils.copyProperties(page1, result);
        Integer current = page1.getCurrent();
        Integer pagesize = page1.getSize();
        Integer begin = (current - 1) * pagesize;
        result.setRecords(listUserWalletUseRecord(userId, pagesize, begin));
        result.setTotal(countNum(userId));
        return result;
    }

    @Override
    public Integer countNum(Integer userId, Integer operationType) {
        return userWalletUseRecordDao.countNum1(userId, operationType)/2;
    }

    @Override
    public Integer countNum(Integer userId) {
        return userWalletUseRecordDao.countNum2(userId)/2;
    }

    @Override
    public List<UserWalletUseRecordDto> listUserWalletUseRecord(Integer userId, Integer operationType, Integer pagesize, Integer begin) {
        return userWalletUseRecordDao.listUserWalletUseRecord1(userId, operationType, pagesize, begin);
    }

    @Override
    public List<UserWalletUseRecordDto> listUserWalletUseRecord(Integer userId, Integer pagesize, Integer begin) {
        return userWalletUseRecordDao.listUserWalletUseRecord2(userId, pagesize, begin);
    }
}
