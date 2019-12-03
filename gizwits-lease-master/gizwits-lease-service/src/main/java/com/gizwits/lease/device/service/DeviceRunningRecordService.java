package com.gizwits.lease.device.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.lease.device.entity.DeviceRunningRecord;
import com.gizwits.lease.device.vo.DeviceRunningRecordForListDto;
import com.gizwits.lease.device.vo.DeviceRunningRecordForQueryDto;

/**
 * <p>
 * 设备运行记录表 服务类
 * </p>
 *
 * @author zhl
 * @since 2017-07-12
 */
public interface DeviceRunningRecordService extends IService<DeviceRunningRecord> {

    /**
     * 列表
     */
    Page<DeviceRunningRecordForListDto> list(Pageable<DeviceRunningRecordForQueryDto> pageable);
}
