package com.gizwits.lease.product.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.SysConfigUtils;
import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.constant.BooleanEnum;
import com.gizwits.lease.constant.DeviceNormalStatus;
import com.gizwits.lease.constant.DeviceStockTemplate;
import com.gizwits.lease.constant.ProductCategoryExcelTemplate;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.dto.DeviceExport;
import com.gizwits.lease.device.entity.dto.DeviceExportResultDto;
import com.gizwits.lease.device.service.DeviceAssignService;
import com.gizwits.lease.device.service.DeviceQrcodeService;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.device.service.impl.DeviceQrcodeServiceImpl;
import com.gizwits.lease.device.vo.DeviceQrcodeExportDto;
import com.gizwits.lease.enums.DeviceExPortFailType;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.product.dto.ProductExportResultDto;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.product.entity.ProductCategory;
import com.gizwits.lease.product.service.ProductCategoryService;
import com.gizwits.lease.product.service.ProductQrcodeService;
import com.gizwits.lease.product.service.ProductService;
import com.gizwits.lease.utils.JxlExcelUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductQrcodeServiceImpl implements ProductQrcodeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceQrcodeServiceImpl.class);

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DeviceAssignService deviceAssignService;



    @Override
    public List<ProductExportResultDto> importExcel(List<ProductCategoryExcelTemplate> validData, Integer productId) {
        if (CollectionUtils.isEmpty(validData)) {
            LeaseException.throwSystemException(LeaseExceEnums.EXCEL_NO_DATA);
        }
        LOGGER.info("待导入的数据共:{}条", validData.size());
        List<ProductExportResultDto> resultDtoList = new ArrayList<>();
        List<String> failMacList = new LinkedList<>();
        List<ProductCategory> forUpdateList = new LinkedList<>();
        Date now = new Date();
        for (ProductCategoryExcelTemplate data : validData) {
            // 防止导入时文件格式为数值，将科学计数法转为数值
            String name = data.getName().trim();
            String categoryType = data.getCategoryType().trim();

            // 查看导入文件是否有重复数据
            if (!resolveDate(validData, data).equals("")) {
                String dates = resolveDate(validData, data);
                ProductExportResultDto dto = new ProductExportResultDto();
                if (dates.equals("name")){
                    dto.setName(name);
                    failMacList.add(data.getName());
                }if (dates.equals("categoryType")){
                    dto.setName(categoryType);
                    failMacList.add(data.getCategoryType());
                }
                dto.setReason(DeviceExPortFailType.FILA_DATA_DUPLICATION.getDesc());
                resultDtoList.add(dto);
                continue;
            }
            ProductCategory productCategory = resolveOne(resultDtoList, data, now ,productId);
            if (productCategory == null) {
                failMacList.add(data.getName());
            } else {
                forUpdateList.add(productCategory);
            }
        }

        if (forUpdateList.size() > 0) {
            productCategoryService.insertBatch(forUpdateList);
        }
        LOGGER.info("已导入{}条设备数据", forUpdateList.size());
        if (failMacList.size() > 0) {
            throwImportFailException(failMacList);
        }
        return resultDtoList;
    }

    private void throwImportFailException(List<String> failMacList) {
        LOGGER.info("======>>>>导入失败的产品名称：" + failMacList.toString());
    }

    private String resolveDate(List<ProductCategoryExcelTemplate> validData, ProductCategoryExcelTemplate data) {
        List<ProductCategoryExcelTemplate> origin = new ArrayList<>();
        origin.addAll(validData);
        origin.remove(data);
        for (ProductCategoryExcelTemplate template : origin) {
            template.setName(template.getName());
            if (template.getName().equalsIgnoreCase(data.getName())) {
                return "name";
            }
            template.setCategoryType(template.getCategoryType());
            if (template.getCategoryType().equalsIgnoreCase(data.getCategoryType())) {
                return "categoryType";
            }
        }
        return "";
    }

    private ProductCategory resolveOne(List<ProductExportResultDto> resultDto, ProductCategoryExcelTemplate data, Date now,Integer productId) {
        String name = data.getName().trim();
        ProductCategory productCategoryByName = getProductCategoryByName(name);
        ProductExportResultDto dto = new ProductExportResultDto();
        dto.setName(data.getName());

        if (null != productCategoryByName) {
            dto.setReason(LeaseExceEnums.PRODUCT_IS_EXISTS.getMessage());
            resultDto.add(dto);
            return null;
        }
        Product product = productService.selectById(productId);
        if (null == product) {
            dto.setReason(LeaseExceEnums.PRODUCTCATEGORY_IS_EXISTS.getMessage());
            resultDto.add(dto);
            return null;
        } else {

            SysUser current = sysUserService.getCurrentUserOwner();
            ProductCategory productCategory = new ProductCategory();
            productCategory.setCtime(new Date());
            productCategory.setUtime(productCategory.getCtime());
            productCategory.setName(name);
            productCategory.setParentCategoryId((Integer) ObjectUtils.defaultIfNull(current.getSysUserId(), 0));
            productCategory.setSysUserId(current.getId());
            productCategory.setSysUserName(current.getUsername());
            productCategory.setRemark("批量插入");
            productCategory.setCategoryId(product.getCategoryId());  //品类ID
            productCategory.setCategoryCount(data.getCategoryCount());  //安全库存
            productCategory.setCategoryType(data.getCategoryType());  //型号
            productCategory.setIsDeleted(BooleanEnum.FALSE.getCode());
            return productCategory;
        }
    }

    private ProductCategory getProductCategoryByName(String name){
        return productCategoryService.selectOne(new EntityWrapper<ProductCategory>().eq("name", name).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }

}
