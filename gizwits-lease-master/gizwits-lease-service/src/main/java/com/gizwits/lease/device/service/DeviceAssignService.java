package com.gizwits.lease.device.service;

import java.util.List;

import com.gizwits.lease.common.perm.dto.AssignDestinationDto;
import com.gizwits.lease.device.entity.dto.DeviceExportResultDto;
import com.gizwits.lease.device.entity.dto.DeviceForAssignDto;
import com.gizwits.lease.device.entity.dto.DeviceForUnbindDto;

/**
 * Service - 设备分配
 *
 * @author lilh
 * @date 2017/8/24 18:35
 */
public interface DeviceAssignService {

    /**
     * 预分配
     */
    List<AssignDestinationDto> preAssign();

    /**
     * 分配
     */
    boolean assign(DeviceForAssignDto dto);

    /**
     * 批量出库
     */
    List<String> outOfStock(DeviceForAssignDto dto);

    /**
     * 逐层分配
     */
    boolean assign(String qrcode, Integer assignedId);

    /**
     * 解绑
     */
    boolean unbind(DeviceForUnbindDto dto);
}
