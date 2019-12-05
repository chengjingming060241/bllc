package com.gizwits.lease.device.dao;

import com.gizwits.lease.device.entity.UserBindDevice;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gizwits.lease.device.vo.UserBindDeviceListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
  * 用户绑定设备表 Mapper 接口
 * </p>
 *
 * @author yinhui
 * @since 2018-01-22
 */
public interface UserBindDeviceDao extends BaseMapper<UserBindDevice> {

    List<UserBindDeviceListVo> findBindDeviceByUserId(@Param("userId") Integer userid,@Param("current") Integer current,@Param("size") Integer size);
    Integer findBindDeviceByUserIdCount(@Param("userId") Integer userid);
}