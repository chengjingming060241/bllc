package com.gizwits.lease.stat.service;

import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.lease.stat.dto.StatDeviceTrendDto;
import com.gizwits.lease.stat.entity.StatDeviceTrend;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.lease.stat.vo.StatTrendVo;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 设备趋势统计表 服务类
 * </p>
 *
 * @author gagi
 * @since 2017-07-11
 */
public interface StatDeviceTrendService extends IService<StatDeviceTrend> {

    /**
     * @param today                今天
     * @param yesterday            昨天
     * @param ownerId              归属id
     */
    void setDataForDeviceTrendForOwnerId(Date today, Date yesterday, Integer ownerId);

    void setDataForStatDeviceTrend();
    void statisticsDeviceTrend();

    List<StatTrendVo> getNewTrend(StatDeviceTrendDto statDeviceTrendDto);

    List<StatTrendVo> getActiveTrend(StatDeviceTrendDto statDeviceTrendDto);

    List<StatTrendVo> getUserPercentTrend(StatDeviceTrendDto statDeviceTrendDto, SysUser currentUser, List<Integer> ids);

    List<StatTrendVo> getNewActivatedTrend(StatDeviceTrendDto statDeviceTrendDto, SysUser currentUser, List<Integer> ids);

    List<StatTrendVo> getFaultDeviceTrend(StatDeviceTrendDto statDeviceTrendDto);

    List<StatTrendVo> getAlertDeviceTrend(StatDeviceTrendDto statDeviceTrendDto);

    /**
    * 设备总数趋势
     */
    List<StatTrendVo> allTotalTrend(StatDeviceTrendDto statDeviceTrendDto);
}
