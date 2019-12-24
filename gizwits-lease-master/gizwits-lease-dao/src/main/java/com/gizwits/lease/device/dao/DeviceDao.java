package com.gizwits.lease.device.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gizwits.lease.device.entity.Device;

import com.gizwits.lease.device.entity.DevicePlan;
import com.gizwits.lease.device.entity.dto.DeviceQueryDto;
import com.gizwits.lease.stat.vo.StatAlarmWidgetVo;
import com.gizwits.lease.stat.vo.StatDeviceStatisticsVo;
import com.gizwits.lease.stat.vo.StatDeviceWidgetVo;
import com.gizwits.lease.stat.vo.StatLocationVo;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * <p>
 * 设备表 Mapper 接口
 * </p>
 *
 * @author zhl
 * @since 2017-07-11
 */
public interface DeviceDao extends BaseMapper<Device> {
    /**
     * 根据sysUserId获取设备的总数量
     *
     * @return int
     */
    Integer getTotalBySysUserIdAndIsNotDeleted(@Param("sysUserId") int sysUserId, @Param("productId") Integer productId, @Param("endTime") Date endTime);

    /**
     * 获取设备中存在的所有的sysUserId
     */
    List<Integer> getDiffSysUserId();

    /**
     * 获取device表中不重复的ownerId
     */
    Set<Integer> getDiffOwnerId();

    /**
     * 根据sysUserId获取productIdList
     */
    List<Integer> getProductId(@Param("sysUserId") Integer sysUserId);

    /**
     * 获取device中的productId和productKey
     */
    List<Map<String, Object>> getProductIdAndKey();

    Device findByMacAndProductKey(@Param("mac") String mac, @Param("productKey") String productKey);

    /**
     * 根据参数查询date对应的设备新增数量
     */
    Integer getNewCount(@Param("sysUserId") Integer sysUserId, @Param("productId") Integer productId, @Param("start") Date start,@Param("end")Date end);

    /**
     * 根据参数查询date对应的设备活跃数量
     */
    Integer getActiveCount(@Param("sysUserId") Integer sysUserId, @Param("productId") Integer productId, @Param("start") Date start,@Param("end")Date end);

    /**
     * 根据参数查询date对应的设备订单数量
     */
    Integer getOrderedCount(@Param("sysUserId") Integer sysUserId, @Param("productId") Integer productId,@Param("start") Date start,@Param("end")Date end);


    Integer getAlarmCount(@Param("sysUserId") Integer sysUserId, @Param("productId") Integer productId);

    Integer getWarnCount(@Param("sysUserId") Integer sysUserId, @Param("productId") Integer productId);

    String getSnoByOpenid(@Param("openid") String openid, @Param("status") Integer status);

    /**
     * 未分配投放点的设备
     */
    Integer getDeviceWithoutArea(@Param("ownerId") Integer ownerId, @Param("productId") Integer productId);

    /**
     * 统计在线设备数
     */
    Integer selectOnlineCount(@Param("sysUserId") Integer sysUserId, @Param("productId") Integer productId);

    /**
     * 统计已上线过的设备数量
     */
    Integer selectActivatedCount(@Param("sysUserId") Integer sysUserId, @Param("productId") Integer productId, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 统计故障设备数
     */
    Integer selectFaultCount(@Param("sysUserId") Integer sysUserId, @Param("productId") Integer productId, @Param("endTime") Date endTime);

    /**
     * 统计报警设备数
     */
    Integer selectAlertCount(@Param("sysUserId") Integer sysUserId, @Param("productId") Integer productId, @Param("endTime") Date endTime);

    int updateActivateStatus(@Param("sno") String sno, @Param("time") Date time);

    List<Map<String,Number>> findProvinceAndCount(@Param("sysUserId") Integer sysUserId, @Param("productId") Integer productId);

    //==================START=============================//
    StatDeviceWidgetVo selectTotalDeviceCount(@Param("productId") Integer productId, @Param("date") Date date);

    StatAlarmWidgetVo getAlarmAndFaultWidget(@Param("productId") Integer productId);
    /**
     * 设备分布，排行榜，省
     */
    List<StatLocationVo> ditributionByProvince(@Param("productId") Integer productId);
    /**
     * 设备分布，排行榜，市
     */
    List<StatLocationVo> ditributionByCity(@Param("productId") Integer productId,@Param("province") String province);
    /**
     * 根据日期统计设备趋势情况
     */
    StatDeviceStatisticsVo getDeviceStatictics(@Param("productId") Integer productId,@Param("date") Date date);

    /**
     * 设备列表
     */
    List<Device> selectDevices(DeviceQueryDto deviceQueryDto);
    Integer selectDevicesCount(DeviceQueryDto deviceQueryDto);

    List<Device> getUserRoomDevice(@Param("roomId") Integer roomId);

    //==================END=============================//
}