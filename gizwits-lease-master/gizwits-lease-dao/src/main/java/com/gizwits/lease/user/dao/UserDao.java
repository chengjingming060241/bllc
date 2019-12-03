package com.gizwits.lease.user.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gizwits.lease.stat.vo.StatUserWidgetVo;
import com.gizwits.lease.user.dto.QueryForUserListDTO;
import com.gizwits.lease.user.dto.UserForQueryDto;
import com.gizwits.lease.user.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表,不要前缀,因为用户模块计划抽象成通用功能 Mapper 接口
 * </p>
 *
 * @author rongmc
 * @since 2017-06-28
 */
public interface UserDao extends BaseMapper<User> {

    /**
     * 用户数量统计，详细看用户归属描述
     *
     * @param query
     * @return
     */
    int countAffiliation(@Param("query") QueryForUserListDTO query);

    /**
     * 用户列表查询，详细看用户归属描述
     *
     * @param query
     * @param offset
     * @param size
     * @return
     */
    List<User> listAffiliation(@Param("query") QueryForUserListDTO query, @Param("offset") Integer offset, @Param("size") Integer size);

    /**
     * 用户看板统计，一天内控制过设备视为活跃用户
     */
    int getUserActive(@Param("day") Integer day, @Param("ownerIds") List<Integer> ownerIds);

    int getNewUser(@Param("day") Integer day, @Param("ownerIds") List<Integer> ownerId);

    User findByUsername(String username);

    /**
     * @param openid
     * @return
     */
    User findByOpenid(@Param("openid") String openid);

    List<Integer> getDiffSysUserId();


    List<String> findDiffProvince(@Param("sysUserId") Integer sysUserId);

    List<Map<String, Object>> findProvinceAndCount(@Param("sysUserId") Integer sysUserId);

    Map<String, Number> getTrendDate(@Param("sysUserId") Integer sysUserId, @Param("date") Date date);

    Integer getTotal(@Param("sysUserId") Integer sysUserId);

    Integer getNewByDate(@Param("sysUserId") Integer sysUserId, @Param("start") Date start, @Param("end") Date end);

    Integer getOrderedCount(@Param("sysUserId") Integer sysUserId);

    Integer getNewOrderedCount(@Param("sysUserId") Integer sysUserId, @Param("start") Date start, @Param("end") Date end);

    Integer getActive(@Param("sysUserId") Integer sysUserId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    Integer getSex(@Param("sysUserId") Integer sysUserId, @Param("gender") Integer gender);

    List<Map<Integer, Number>> getOrderTimes(@Param("sysUserId") Integer sysUserId, @Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

    List<User> findByUnionids(@Param("unionids") List<String> unionids, @Param("sysUserIds") List<Integer> sysUserIds);

    List<User> listPage(UserForQueryDto query);

    List<User> selectPageGMDS(UserForQueryDto query);

    int findTotalSize(UserForQueryDto query);

    int findTotalSizeGMDS(UserForQueryDto query);

    List<String> getAllAppUsersMobile();

    /**
     * 获取卡券最后到期时间
     * @param userId
     * @return
     */
    Date findMaxEndTimeByOpenid(@Param("userId") String userId);


    //=====================//
    StatUserWidgetVo getUserWidget(@Param("date") Date date);
    //========================//
}