package com.gizwits.lease.device.service;

import java.util.List;

import com.gizwits.lease.common.perm.dto.AssignDestinationDto;
import com.gizwits.lease.constant.DeviceLaunchAreaExcelTemplate;
import com.gizwits.lease.device.entity.dto.DeviceLaunchAreaExportResultDto;
import com.gizwits.lease.device.entity.dto.DeviceLaunchAreaForAssignDto;
import com.gizwits.lease.device.entity.dto.DeviceLaunchAreaForUnbindDto;

/**
 * @author lilh
 * @date 2017/9/2 17:39
 */
public interface DeviceLaunchAreaAssignService {

    /**
     * 预分配
     */
    List<AssignDestinationDto> preAssign();

    /**
     * 分配
     */
    boolean assign(DeviceLaunchAreaForAssignDto dto);

    /**
     * 解绑
     */
    boolean unbind(DeviceLaunchAreaForUnbindDto dto);

    /**
     * 是否可修改
     */
    boolean canModify(Integer launchAreaId);

    /**
     * 导入产品数据
     */
    List<DeviceLaunchAreaExportResultDto> importExcel(List<DeviceLaunchAreaExcelTemplate> needToImportData);
}
