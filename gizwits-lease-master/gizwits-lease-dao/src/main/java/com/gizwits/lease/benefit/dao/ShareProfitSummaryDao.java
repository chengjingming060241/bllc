package com.gizwits.lease.benefit.dao;

import com.gizwits.lease.benefit.entity.ShareProfitSummary;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * <p>
  * 分润账单汇总 Mapper 接口
 * </p>
 *
 * @author yinhui
 * @since 2018-05-05
 */
public interface ShareProfitSummaryDao extends BaseMapper<ShareProfitSummary> {

    /**
     * 统计分润账单汇总的订单数和订单金额
     */
    ShareProfitSummaryOrderDto countOrderForSummary(@Param("id") String id);

    /**
     * 计算分润账单汇总的分润金额
     */
    BigDecimal sumShareMoney(@Param("id") String id);
}