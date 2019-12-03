package com.gizwits.lease.stat.service;

import com.baomidou.mybatisplus.service.IService;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.lease.stat.entity.StatDeviceOrderWidget;
import com.gizwits.lease.stat.vo.StatAlarmWidgetVo;
import com.gizwits.lease.stat.vo.StatDeviceWidgetVo;
import com.gizwits.lease.stat.vo.StatOrderWidgetVo;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 设备订单看板数据统计表 服务类
 * </p>
 *
 * @author gagi
 * @since 2017-07-18
 */
public interface StatDeviceOrderWidgetService extends IService<StatDeviceOrderWidget> {
    /**
     *
     * @param firstDay 本月第一天
     * @param now 当前时间
     * @param yesterday 昨天
     * @param beforeYesterday 前天
     * @param ownerId 设备归属者
     */
    void setDataForWidgetByOwnerId(Date firstDay, Date now, Date yesterday, Date beforeYesterday, Date today, Date tomorrow, Integer ownerId);

    void setDataForWidget();

    StatOrderWidgetVo orderWidget(Integer productId, SysUser currentUser, List<Integer> ids);

    StatDeviceWidgetVo deviceWidget(Integer productId, SysUser currentUser, List<Integer> ids);

    StatAlarmWidgetVo alarmWidget(Integer productId, SysUser currentUser, List<Integer> ids);
}
