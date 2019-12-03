package com.gizwits.lease.wallet.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.lease.enums.RechargeType;
import com.gizwits.lease.wallet.dto.RechargeDto;
import com.gizwits.lease.wallet.dto.RechargeMoneyDto;
import com.gizwits.lease.wallet.entity.RechargeMoney;
import com.gizwits.lease.wallet.dao.RechargeMoneyDao;
import com.gizwits.lease.wallet.service.RechargeMoneyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 充值优惠表 服务实现类
 * </p>
 *
 * @author yinhui
 * @since 2017-08-21
 */
@Service
public class RechargeMoneyServiceImpl extends ServiceImpl<RechargeMoneyDao, RechargeMoney> implements RechargeMoneyService {

    @Override
    public RechargeDto getRechargeMoney(Integer projectId) {
        RechargeDto rechargeDto = new RechargeDto();
        List<RechargeMoneyDto> rechargeMoneyDtos = new ArrayList<>();
        EntityWrapper<RechargeMoney> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("project_id",projectId).eq("type", RechargeType.FIXED.getCode());
        List<RechargeMoney> list = selectList(entityWrapper);
        for(RechargeMoney rechargeMoney:list){
            RechargeMoneyDto rechargeMoneyDto = new RechargeMoneyDto();
            rechargeMoneyDto.setId(rechargeMoney.getId());
            rechargeMoneyDto.setRechargeMoney(rechargeMoney.getChargeMoney());
            rechargeMoneyDto.setDiscountMoney(rechargeMoney.getDiscountMoney());
            rechargeMoneyDto.setSort(rechargeMoney.getSort());
            rechargeMoneyDtos.add(rechargeMoneyDto);
        }
        rechargeDto.setRechargeMoneyDtos(rechargeMoneyDtos);
        return rechargeDto;
    }

    @Override
    public BigDecimal getRate(Integer sysUserId) {
        EntityWrapper<RechargeMoney> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("sys_user_id",sysUserId).eq("type",RechargeType.CUSTOM.getCode());
        RechargeMoney rechargeMoney = selectOne(entityWrapper);
        if(Objects.isNull(rechargeMoney)){
            return  null;
        }
        return rechargeMoney.getRate();
    }

}
