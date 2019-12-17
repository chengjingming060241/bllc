package com.gizwits.lease.device.dao;

import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.UserDevice;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gizwits.lease.device.entity.dto.UserDeviceQueryDto;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
  * 用户绑定设备表 Mapper 接口
 * </p>
 *
 * @author yinhui
 * @since 2017-07-12
 */
@Repository
public interface UserDeviceDao extends BaseMapper<UserDevice> {

    List<Device> selectDeviceByUserId(UserDeviceQueryDto queryDto);
    Integer selectDeviceByUserIdCount(UserDeviceQueryDto queryDto);

}