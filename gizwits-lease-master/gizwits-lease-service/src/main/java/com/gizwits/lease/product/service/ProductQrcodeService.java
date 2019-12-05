package com.gizwits.lease.product.service;

import com.gizwits.lease.constant.DeviceStockTemplate;
import com.gizwits.lease.constant.ProductCategoryExcelTemplate;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.dto.DeviceExport;
import com.gizwits.lease.device.entity.dto.DeviceExportResultDto;
import com.gizwits.lease.device.vo.DeviceQrcodeExportDto;
import com.gizwits.lease.product.dto.ProductExportResultDto;

import java.util.List;

/**
 * @author lilh
 * @date 2017/8/30 16:51
 */
public interface ProductQrcodeService {

    /**
     * 导入产品数据
     */
    List<ProductExportResultDto> importExcel(List<ProductCategoryExcelTemplate> needToImportData , Integer productId);


}
