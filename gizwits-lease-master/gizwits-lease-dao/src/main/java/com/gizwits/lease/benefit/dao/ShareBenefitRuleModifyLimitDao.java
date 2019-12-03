package com.gizwits.lease.benefit.dao;

import com.gizwits.lease.benefit.entity.ShareBenefitRuleModifyLimit;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  * 分润规则修改次数限制 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2017-12-15
 */
public interface ShareBenefitRuleModifyLimitDao extends BaseMapper<ShareBenefitRuleModifyLimit> {

    int insertOrUpdate(ShareBenefitRuleModifyLimit ruleModifyLimit);

}