package com.gizwits.lease.stat.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gizwits.lease.stat.entity.StatDeviceOrderWidget;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * <p>
  * 设备订单看板数据统计表 Mapper 接口
 * </p>
 *
 * @author gagi
 * @since 2017-07-18
 */
public interface StatDeviceOrderWidgetDao extends BaseMapper<StatDeviceOrderWidget> {

    StatDeviceOrderWidget orderWidget(@Param("sysUserId") Integer id, @Param("productId") Integer productId, @Param("date") Date now);

    StatDeviceOrderWidget deviceWidget(@Param("sysUserId") Integer id, @Param("productId") Integer productId, @Param("date") Date now);

    /**
     * @param id
     * @param productId
     * @param now
     * @return
     */
    StatDeviceOrderWidget alarmWidget(@Param("sysUserId") Integer id, @Param("productId") Integer productId, @Param("date") Date now);

    Integer updateByUtimeAndSysUserIdAndProductId(@Param("date") Date now, @Param("sysUserId") Integer sysUserId, @Param("productId") Integer productId,@Param("widget") StatDeviceOrderWidget statDeviceOrderWidget);

    Double getOrderNewPercent(@Param("sysUserId") Integer sysUserId,@Param("productId") Integer productId,@Param("date") Date yesterday);

    StatDeviceOrderWidget deviceWidgetByIds(@Param("ids")List<Integer> ids,@Param("productId") Integer productId,@Param("date") Date now);

    StatDeviceOrderWidget alarmWidgetByIds(@Param("ids")List<Integer> ids,@Param("productId") Integer productId,@Param("date") Date now);

    StatDeviceOrderWidget orderWidgetByIds(@Param("ids")List<Integer> ids,@Param("productId") Integer productId,@Param("date") Date now);

    Date getRecentlyDate();

    void deleteAlreadyEsixtData(@Param("sysUserId") Integer sysUserId,@Param("productList") List<Integer> productList,@Param("date") Date date);

    Integer selectByUtimeAndSysUserIdAndProductId(@Param("date") Date now, @Param("sysUserId") Integer sysUserId, @Param("productId") Integer productId,@Param("widget") StatDeviceOrderWidget statDeviceOrderWidget);

}