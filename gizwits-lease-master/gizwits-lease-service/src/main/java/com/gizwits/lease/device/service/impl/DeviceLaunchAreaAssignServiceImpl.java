package com.gizwits.lease.device.service.impl;

import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.lease.common.perm.CommonRoleResolver;
import com.gizwits.lease.common.perm.DefaultCommonRoleResolverManager;
import com.gizwits.lease.common.perm.SysUserRoleTypeHelper;
import com.gizwits.lease.common.perm.SysUserRoleTypeHelperResolver;
import com.gizwits.lease.common.perm.dto.AssignDestinationDto;
import com.gizwits.lease.constant.BooleanEnum;
import com.gizwits.lease.constant.DeviceLaunchAreaExcelTemplate;
import com.gizwits.lease.constant.DeviceStatus;
import com.gizwits.lease.constant.ProductCategoryExcelTemplate;
import com.gizwits.lease.device.entity.DeviceLaunchArea;
import com.gizwits.lease.device.entity.dto.*;
import com.gizwits.lease.device.service.DeviceLaunchAreaAssignService;
import com.gizwits.lease.device.service.DeviceLaunchAreaService;
import com.gizwits.lease.enums.DeviceExPortFailType;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.product.dto.ProductExportResultDto;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.product.entity.ProductCategory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lilh
 * @date 2017/9/2 17:38
 */
@Service
public class DeviceLaunchAreaAssignServiceImpl implements DeviceLaunchAreaAssignService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DeviceLaunchAreaAssignServiceImpl.class);

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserRoleTypeHelperResolver helperResolver;

    @Autowired
    private DefaultCommonRoleResolverManager resolverManager;

    @Autowired
    private DeviceLaunchAreaService deviceLaunchAreaService;

    @Override
    public List<AssignDestinationDto> preAssign() {
        SysUserRoleTypeHelper helper = helperResolver.resolve(sysUserService.getCurrentUser());
        CommonRoleResolver resolver = resolverManager.getLaunchResolver(helper.getCommonRole());
        if (Objects.nonNull(resolver)) {
            return resolver.resolveAssignDest();
        }
        return Collections.emptyList();
    }

    @Override
    public boolean assign(DeviceLaunchAreaForAssignDto dto) {
        SysUserRoleTypeHelper helper = helperResolver.resolve(sysUserService.getCurrentUser());
        CommonRoleResolver resolver = resolverManager.getLaunchResolver(helper.getCommonRole());
        DeviceForAssignDto assignDto = new DeviceForAssignDto();
        BeanUtils.copyProperties(dto, assignDto);
        return Objects.nonNull(resolver) && resolver.assign(assignDto, helper);
    }

    @Override
    public boolean unbind(DeviceLaunchAreaForUnbindDto dto) {
        SysUserRoleTypeHelper helper = helperResolver.resolve(sysUserService.getCurrentUser());
        CommonRoleResolver resolver = resolverManager.getLaunchResolver(helper.getCommonRole());
        DeviceForUnbindDto unbindDto = new DeviceForUnbindDto();
        BeanUtils.copyProperties(dto, unbindDto);
        return Objects.nonNull(resolver) && resolver.unbind(unbindDto, helper);
    }

    @Override
    public boolean canModify(Integer launchAreaId) {
        if (Objects.nonNull(launchAreaId)) {
            DeviceLaunchArea deviceLaunchArea = deviceLaunchAreaService.selectById(launchAreaId);
            if (Objects.nonNull(deviceLaunchArea)) {
                if (Objects.isNull(deviceLaunchArea.getOwnerId())) {
                    deviceLaunchArea.setOwnerId(deviceLaunchArea.getSysUserId());
                    deviceLaunchAreaService.updateById(deviceLaunchArea);
                }
                return Objects.equals(deviceLaunchArea.getSysUserId(), deviceLaunchArea.getOwnerId());
            }
        }
        return true;
    }

    @Override
    public List<DeviceLaunchAreaExportResultDto> importExcel(List<DeviceLaunchAreaExcelTemplate> validData) {
        if (CollectionUtils.isEmpty(validData)) {
            LeaseException.throwSystemException(LeaseExceEnums.EXCEL_NO_DATA);
        }
        LOGGER.info("待导入的数据共:{}条", validData.size());
        List<DeviceLaunchAreaExportResultDto> resultDtoList = new ArrayList<>();
        List<String> failMacList = new LinkedList<>();
        List<DeviceLaunchArea> forUpdateList = new LinkedList<>();
        Date now = new Date();
        for (DeviceLaunchAreaExcelTemplate data : validData) {
            // 防止导入时文件格式为数值，将科学计数法转为数值
            String name = data.getName().trim();

            // 查看导入文件是否有重复数据
            if (!resolveDate(validData, data)) {
                DeviceLaunchAreaExportResultDto dto = new DeviceLaunchAreaExportResultDto();
                dto.setName(name);
                failMacList.add(data.getName());
                dto.setReason(DeviceExPortFailType.FILA_DATA_DUPLICATION.getDesc());
                resultDtoList.add(dto);
                continue;
            }
            DeviceLaunchArea deviceLaunchArea = resolveOne(resultDtoList, data, now);
            if (deviceLaunchArea == null) {
                failMacList.add(data.getName());
            } else {
                forUpdateList.add(deviceLaunchArea);
            }
        }

        if (forUpdateList.size() > 0) {
            boolean b = deviceLaunchAreaService.insertBatch(forUpdateList);
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

    private boolean resolveDate(List<DeviceLaunchAreaExcelTemplate> validData, DeviceLaunchAreaExcelTemplate data) {
        List<DeviceLaunchAreaExcelTemplate> origin = new ArrayList<>();
        origin.addAll(validData);
        origin.remove(data);
        for (DeviceLaunchAreaExcelTemplate template : origin) {
            template.setName(template.getName());
            if (template.getName().equalsIgnoreCase(data.getName())) {
                return false;
            }

        }
        return true;
    }

    private DeviceLaunchArea resolveOne(List<DeviceLaunchAreaExportResultDto> resultDto, DeviceLaunchAreaExcelTemplate data, Date now) {
        String name = data.getName().trim();
        DeviceLaunchArea deviceLaunchAreaByName = getDeviceLaunchAreaByName(name);
        DeviceLaunchAreaExportResultDto dto = new DeviceLaunchAreaExportResultDto();
        dto.setName(data.getName());

        if (null != deviceLaunchAreaByName) {
            dto.setReason(LeaseExceEnums.PRODUCT_IS_EXISTS.getMessage());
            resultDto.add(dto);
            return null;
        }
        else {

            SysUser current = sysUserService.getCurrentUserOwner();
            DeviceLaunchArea deviceLaunchArea = new DeviceLaunchArea();
            deviceLaunchArea.setCtime(now);
            deviceLaunchArea.setUtime(now);
            deviceLaunchArea.setSysUserId(current.getId());
            deviceLaunchArea.setSysUserName(current.getUsername());
            deviceLaunchArea.setStatus(0);
            deviceLaunchArea.setOwnerId(current.getId());
            deviceLaunchArea.setProvince(data.getProvince());
            deviceLaunchArea.setCity(data.getCity());
            deviceLaunchArea.setArea(data.getArea());
            deviceLaunchArea.setAddress(data.getAddress());
            deviceLaunchArea.setName(name);

            deviceLaunchArea.setIsDelete(BooleanEnum.FALSE.getCode());
            return deviceLaunchArea;
        }
    }

    private DeviceLaunchArea getDeviceLaunchAreaByName(String name){
        return deviceLaunchAreaService.selectOne(new EntityWrapper<DeviceLaunchArea>().eq("name", name).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }
}
