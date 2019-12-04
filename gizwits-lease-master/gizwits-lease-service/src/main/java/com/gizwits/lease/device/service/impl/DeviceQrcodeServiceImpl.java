package com.gizwits.lease.device.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.SysConfigUtils;
import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.constant.BooleanEnum;
import com.gizwits.lease.constant.DeviceExcelTemplate;
import com.gizwits.lease.constant.DeviceNormalStatus;
import com.gizwits.lease.constant.DeviceStatus;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.dto.DeviceExport;
import com.gizwits.lease.device.entity.dto.DeviceExportResultDto;
import com.gizwits.lease.device.service.DeviceAssignService;
import com.gizwits.lease.device.service.DeviceQrcodeService;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.device.vo.DeviceQrcodeExportDto;
import com.gizwits.lease.enums.DeviceActiveStatusType;
import com.gizwits.lease.enums.DeviceExPortFailType;
import com.gizwits.lease.enums.DeviceOriginType;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.product.service.ProductService;
import com.gizwits.lease.utils.JxlExcelUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lilh
 * @date 2017/8/30 16:52
 */
@Slf4j
@Service
public class DeviceQrcodeServiceImpl implements DeviceQrcodeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceQrcodeServiceImpl.class);

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DeviceAssignService deviceAssignService;


    @Override
    public List<Device> resolveDevices(DeviceQrcodeExportDto dto, QrcodeListener qrcodeListener) {
        Product product = resolveProduct(dto.getProductId());
        SysUser current = sysUserService.getCurrentUserOwner();
        List<Device> devices = initDevices(product, current, dto.getCount(), qrcodeListener);
        // deviceService.insertBatch(devices);
        return devices;
    }

    @Override
    public boolean initTemplateExcel() {
        CommonSystemConfig commonSystemConfig = SysConfigUtils.get(CommonSystemConfig.class);
        File template = new File(commonSystemConfig.getQrcodePath() + commonSystemConfig.getDefaultDeviceExcelTemplateFile());
        if (!template.getParentFile().exists()) {
            template.getParentFile().mkdirs();
        }
        if (!template.exists()) {
            //生成模板文件
            try (FileOutputStream outputStream = new FileOutputStream(template)) {
                JxlExcelUtils.listToExcel(null, StringUtils.split(commonSystemConfig.getDefaultDeviceExcelTemplateTitle(), ','), null, "列表", outputStream);
                return true;
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return true;
    }


    @Override
    public boolean gdmsInitTemplateExcel() {
        CommonSystemConfig commonSystemConfig = SysConfigUtils.get(CommonSystemConfig.class);
        File template = new File(commonSystemConfig.getQrcodePath() + commonSystemConfig.getDeviceExportTemplateFile());
        if (!template.getParentFile().exists()) {
            template.getParentFile().mkdirs();
        }
        if (!template.exists()) {
            //生成模板文件
            try (FileOutputStream outputStream = new FileOutputStream(template)) {
                JxlExcelUtils.listToExcel(null, StringUtils.split(commonSystemConfig.getDeviceExportTemplateTitle(), ','), null, "列表", outputStream);
                return true;
            } catch (Exception e) {
                LOGGER.error(e.getMessage());
            }
        }
        return true;
    }

    @Override
    public List<DeviceExportResultDto> importExcel(List<DeviceExcelTemplate> validData) {
        if (CollectionUtils.isEmpty(validData)) {
            LeaseException.throwSystemException(LeaseExceEnums.EXCEL_NO_DATA);
        }
        LOGGER.info("待导入的数据共:{}条", validData.size());
        List<DeviceExportResultDto> resultDtoList = new ArrayList<>();
        List<String> failMacList = new LinkedList<>();
        List<Device> forUpdateList = new LinkedList<>();
        Date now = new Date();
        for (DeviceExcelTemplate data : validData) {
            // 防止导入时文件格式为数值，将科学计数法转为数值
            String mac = data.getMac();
            BigDecimal decimal = new BigDecimal(mac);
            mac = decimal.toPlainString();
            data.setMac(mac);
            // 查看导入文件是否有重复数据
            if (!resolveDate(validData, data)) {
                DeviceExportResultDto dto = new DeviceExportResultDto();
                dto.setMac(data.getMac());
                dto.setReason(DeviceExPortFailType.FILA_DATA_DUPLICATION.getDesc());
                resultDtoList.add(dto);
                failMacList.add(data.getMac());
                continue;
            }
            Device device = resolveOne(resultDtoList, data, now);
            if (device == null) {
                failMacList.add(data.getMac());
            } else {
                forUpdateList.add(device);
            }
        }

        if (forUpdateList.size() > 0) {
            deviceService.updateBatchById(forUpdateList);
        }
        LOGGER.info("已导入{}条设备数据", forUpdateList.size());
        if (failMacList.size() > 0) {
            throwImportFailException(failMacList);
        }
        return resultDtoList;
    }

    private void throwImportFailException(List<String> failMacList) {
//        int showDetailNum = 3;
//        StringBuilder sb = new StringBuilder();
//        for (int i = 0; i < showDetailNum && i < failMacList.size(); i++) {
//            sb.append(failMacList.get(i)).append("、");
//        }
//        sb.deleteCharAt(sb.length() - 1);
//        if (failMacList.size() < showDetailNum) {
//            sb.append("设备导入失败！");
//        } else {
//            sb.append("等").append(failMacList.size()).append("台设备导入失败！请检查数据是否合法或者重复！");
//        }
        log.info("======>>>>导入失败的mac地址：" + failMacList.toString());
//        LeaseException.throwSystemException(LeaseExceEnums.IMPORT_FAIL, sb.toString());
    }

    private boolean resolveDate(List<DeviceExcelTemplate> validData, DeviceExcelTemplate data) {
        List<DeviceExcelTemplate> origin = new ArrayList<>();
        origin.addAll(validData);
        origin.remove(data);
        String dataMac = data.getMac();
        for (DeviceExcelTemplate template : origin) {
            String mac = template.getMac();
            BigDecimal decimal = new BigDecimal(mac);
            mac = decimal.toPlainString();
            template.setMac(mac);
            if (template.getQrcode().equalsIgnoreCase(data.getQrcode()) || mac.equalsIgnoreCase(dataMac)) {
                return false;
            }
        }
        return true;
    }

    private Device resolveOne(List<DeviceExportResultDto> resultDto, DeviceExcelTemplate data, Date now) {
        String qrcode = data.getQrcode().trim();
        String mac = data.getMac().trim();
        Device device = deviceService.getDeviceByQrcode(qrcode);
        DeviceExportResultDto dto = new DeviceExportResultDto();
        dto.setMac(data.getMac());

        if (null == device) {
            dto.setReason(LeaseExceEnums.QECODE_NOT_EXIST.getMessage());
            resultDto.add(dto);
            return null;
        }
        if (!device.getStatus().equals(DeviceNormalStatus.WAIT_TO_ENTRY.getCode())) {
            dto.setReason("二维码信息已导入设备");
            resultDto.add(dto);
            return null;
        }
        if (deviceService.judgeMacIsExsit(mac)) {
            dto.setReason(DeviceExPortFailType.DATA_ERROR_IN_DATABASE.getDesc());
            resultDto.add(dto);
            return null;
        } else {
            Device forUpdate = new Device();
            forUpdate.setSno(device.getSno());
            forUpdate.setUtime(now);
            forUpdate.setStatus(DeviceNormalStatus.ENTRY.getCode());
            forUpdate.setMac(mac);
            forUpdate.setName(device.getMac());
            forUpdate.setIsDeleted(BooleanEnum.FALSE.getCode());
            return forUpdate;
        }
    }

    private List<Device> initDevices(Product product, SysUser current, Integer count, QrcodeListener qrcodeListener) {
        return IntStream.range(0, count).mapToObj(item -> {
            Device device = new Device();
            device.setProductId(product.getId());
            device.setProductName(product.getName());
            device.setOwnerId(current.getId());
            device.setSysUserId(current.getId());
            device.setStatus(DeviceNormalStatus.WAIT_TO_ENTRY.getCode());
            device.setSno(deviceService.getSno());
            //把mac和name设置成sno,入库后再更新
            device.setMac(device.getSno());
            device.setName(device.getSno().substring(0, 10));
            device.setCtime(new Date());
            device.setUtime(new Date());
            //创建二维码及设备认证
            device = deviceService.createQrcodeAndAuthDevice(device, product, current, qrcodeListener);
            // 创建一次二维码就插入一次设备，避免报错后丢失前面创建的设备，导致浪费微信的二维码配额。
            deviceService.insert(device);
            return device;
        }).collect(Collectors.toList());
    }

    private Product resolveProduct(Integer productId) {
        if (Objects.isNull(productId)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        Product product = productService.selectOne(new EntityWrapper<Product>().eq("id", productId).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        if (Objects.isNull(product)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        return product;
    }

    //GDMS的设备导入方式不需要了

//    @Override
//    public boolean importDeviceExcel(List<DeviceExport> validData, Integer productId) {
//        SysUser current = sysUserService.getCurrentUserOwner();
//        if (CollectionUtils.isEmpty(validData)) {
//            LeaseException.throwSystemException(LeaseExceEnums.EXCEL_NO_DATA);
//        }
//        LOGGER.info("待导入的数据共:{}条", validData.size());
//        Product product = productService.getProductByProductId(productId);
//        List<String> failMacList = new LinkedList<>();
//        List<Device> forInsertList = new LinkedList<>();
//        Date now = new Date();
//        //将数据插入数据库
//        for (DeviceExport item : validData) {
//            if (ParamUtil.isNullOrEmptyOrZero(deviceService.getDeviceByMac(item.getMac()))) {
//                Device device = new Device();
//                device.setSno(deviceService.getSno());
//                device.setUtime(now);
//                device.setCtime(now);
//                device.setMac(item.getMac());
//                device.setName(item.getMac());
//                device.setActivateStatus(DeviceActiveStatusType.UNACTIVE.getCode());
//                device.setOrigin(DeviceOriginType.INPUT.getCode());
//                if (!ParamUtil.isNullOrEmptyOrZero(product)) {
//                    device.setProductId(productId);
//                    device.setProductName(product.getName());
//                }
//                device.setSysUserId(current.getId());
//                device.setOwnerId(current.getId());
//                forInsertList.add(device);
//            }else {
//                failMacList.add(item.getMac());
//            }
//        }
//        if (forInsertList.size() > 0) {
//            deviceService.insertBatch(forInsertList);
//        }
//        LOGGER.info("成功导入数据共：{}条", forInsertList.size());
//        if (failMacList.size() > 0) {
//            throwImportFailException(failMacList);
//        }
//        return true;
//    }

    @Override
    public List<DeviceExportResultDto> importDeviceExcelForAssign(List<DeviceExport> validData) {
        SysUser current = sysUserService.getCurrentUserOwner();
        if (CollectionUtils.isEmpty(validData)) {
            LeaseException.throwSystemException(LeaseExceEnums.EXCEL_NO_DATA);
        }
        LOGGER.info("待导入的数据共:{}条", validData.size());
        List<DeviceExportResultDto> resultDtoList = new ArrayList<>();
        int num = 0;
        List<String> failMacList = new LinkedList<>();
        for (DeviceExport item : validData) {
            try {
                if (deviceAssignService.assign(item.getQrcode(), current.getId())) {
                    num++;
                } else {
                    DeviceExportResultDto resultDto = new DeviceExportResultDto();
                    resultDto.setMac(item.getQrcode());
                    resultDto.setReason("分配失败,原因未知");
                    log.error("导入失败，原因未知");
                    failMacList.add(item.getQrcode());
                }
            } catch (Exception e) {
                DeviceExportResultDto resultDto = new DeviceExportResultDto();
                resultDto.setMac(item.getQrcode());
                resultDto.setReason(e.getMessage());
                resultDtoList.add(resultDto);
                failMacList.add(item.getQrcode());
                log.error("导入失败", e);

            }
        }
        LOGGER.info("成功导入数据共：{}条", num);
        if (failMacList.size() > 0) {
            throwImportFailException(failMacList);
        }
        return resultDtoList;
    }

}
