package com.gizwits.lease.device.service;

import java.util.List;

import com.gizwits.lease.constant.DeviceExcelTemplate;
import com.gizwits.lease.constant.DeviceStockTemplate;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.dto.DeviceExport;
import com.gizwits.lease.device.entity.dto.DeviceExportResultDto;
import com.gizwits.lease.device.vo.DeviceQrcodeExportDto;

/**
 * @author lilh
 * @date 2017/8/30 16:51
 */
public interface DeviceQrcodeService {

    interface QrcodeListener{
        boolean doAfterCreateQrcode(String fileName);
    }

    /**
     * 预插入设备
     */
    List<Device> resolveDevices(DeviceQrcodeExportDto dto, QrcodeListener qrcodeListener);

    /**
     * 初始化模板数据
     */
    boolean initTemplateExcel();


    boolean gdmsInitTemplateExcel();

    /**
     * 导入数据
     */
    List<DeviceExportResultDto> importExcel(List<DeviceStockTemplate> needToImportData);

//    boolean importDeviceExcel(List<DeviceExport> validData, Integer productId);

    /**
     * 代理商和运营商导入设备，执行分配流程
     */
    List<DeviceExportResultDto> importDeviceExcelForAssign(List<DeviceExport> validData);
}
