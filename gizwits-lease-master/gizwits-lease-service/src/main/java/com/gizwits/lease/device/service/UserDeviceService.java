package com.gizwits.lease.device.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.lease.device.entity.UserDevice;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.lease.device.entity.dto.*;

import java.util.List;

/**
 * <p>
 * 用户绑定设备表 服务类
 * </p>
 *
 * @author yinhui
 * @since 2017-07-12
 */
public interface UserDeviceService extends IService<UserDevice> {

    Boolean add(UserBindDeviceDto deviceDto);

    Boolean deleteBind(List<String> macs);

    Page<UserDevice> userList(Pageable<DeviceSnoDto> pageable);

    Page<UserDeviceDto> deviceList(Pageable<UserDeviceQueryDto> pageable);

    Page getUserDeviceList(Pageable<UserDeviceQueryDto> pageable);

    Boolean deviceMove(AppDeviceMoveDto dto);
}
