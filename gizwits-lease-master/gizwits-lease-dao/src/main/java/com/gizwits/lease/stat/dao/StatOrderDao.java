package com.gizwits.lease.stat.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gizwits.lease.stat.dto.StatOrderAnalysisDto;
import com.gizwits.lease.stat.entity.StatOrder;
import com.gizwits.lease.stat.vo.StatOrderAnalysisVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单分析统计表 Mapper 接口
 * </p>
 *
 * @author gagi
 * @since 2017-07-11
 */
public interface StatOrderDao extends BaseMapper<StatOrder> {
    List<StatOrder> getDistinctFromOrder(@Param("start") Date start,@Param("end") Date end, @Param("statusList") List<Integer> statusList);

    StatOrder calculateOrderAnalysis(@Param("date") Date date, @Param("sno") String sno, @Param("launchAreaId") Integer launchAreaId, @Param("sysUserId") Integer sysUserId, @Param("statusList") List<Integer> statusList);
    List<StatOrderAnalysisVo> getOrderAnalysisByIds(@Param("dto") StatOrderAnalysisDto statOrderAnalysisDto, @Param("ids") List<Integer> ids);
}