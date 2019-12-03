package com.gizwits.lease.wallet.dao;

import com.gizwits.lease.wallet.dto.DepositListDto;
import com.gizwits.lease.wallet.dto.DepositQueryDto;
import com.gizwits.lease.wallet.entity.UserWalletChargeOrder;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
  * 用户钱包充值单表 Mapper 接口
 * </p>
 *
 * @author yinhui
 * @since 2017-07-31
 */
public interface UserWalletChargeOrderDao extends BaseMapper<UserWalletChargeOrder> {

    List<DepositListDto> listDeposit(DepositQueryDto depositQueryDto);

    Integer countNum(DepositQueryDto depositQueryDto);

}