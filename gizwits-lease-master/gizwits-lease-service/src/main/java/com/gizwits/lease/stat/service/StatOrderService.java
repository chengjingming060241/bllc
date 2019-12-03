package com.gizwits.lease.stat.service;

import com.baomidou.mybatisplus.service.IService;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.stat.dto.StatOrderAnalysisDto;
import com.gizwits.lease.stat.entity.StatOrder;
import com.gizwits.lease.stat.vo.StatOrderAnalysisVo;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 订单分析统计表 服务类
 * </p>
 *
 * @author gagi
 * @since 2017-07-11
 */
public interface StatOrderService extends IService<StatOrder> {
    void setDataForStatByDevice(Date date, String sno, Integer launchAreaId, Integer sysUserId);
    void setDataForStatOrder();
    void setDataForStatOrder(Date date);

    List<StatOrderAnalysisVo> getOrderAnalysis(StatOrderAnalysisDto statOrderAnalysisDto, SysUser currentUser, List<Integer> ids);
}
