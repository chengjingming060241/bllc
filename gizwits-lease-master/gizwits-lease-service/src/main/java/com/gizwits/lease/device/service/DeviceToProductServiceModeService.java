package com.gizwits.lease.device.service;

import com.gizwits.lease.device.entity.DeviceToProductServiceMode;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.lease.device.entity.dto.DeviceAssignDto;
import com.gizwits.lease.device.entity.dto.DeviceAssignMoreModeDto;

import java.util.List;

/**
 * <p>
 * 设备收费模式表(多对多) 服务类
 * </p>
 *
 * @author yinhui
 * @since 2018-01-05
 */
public interface DeviceToProductServiceModeService extends IService<DeviceToProductServiceMode> {

    void batchInsertBySno(DeviceAssignDto deviceAssignDto);

    void batchInsertOrUpdate(DeviceAssignMoreModeDto dto);

    boolean deleteBySno(String sno);

    List<Integer> resolveServiceModeIdBySno(String sno);

    /**
     * 查询使用该收费模式的设备数量
     */
    Integer countDeviceByServiceMode(Integer serviceMode);
}
