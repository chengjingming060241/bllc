package com.gizwits.lease.wallet.dao;

import com.gizwits.lease.wallet.dto.UserWalletUseRecordDto;
import com.gizwits.lease.wallet.entity.UserWalletUseRecord;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户钱包操作记录表 Mapper 接口
 * </p>
 *
 * @author yinhui
 * @since 2017-07-28
 */
public interface UserWalletUseRecordDao extends BaseMapper<UserWalletUseRecord> {

    List<UserWalletUseRecordDto> listUserWalletUseRecord1(@Param("userId") Integer userId, @Param("operation_type") Integer operatoinType,
                                                          @Param("pagesize") Integer pagesize, @Param("begin") Integer begin);

    Integer countNum1(@Param("userId") Integer userId, @Param("operation_type") Integer operatoinType);

    List<UserWalletUseRecordDto> listUserWalletUseRecord2(@Param("userId") Integer userId,
                                                         @Param("pagesize") Integer pagesize, @Param("begin") Integer begin);

    Integer countNum2(@Param("userId") Integer userId);

}