package com.gizwits.lease.device.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.QueryResolverUtils;
import com.gizwits.lease.device.dao.DeviceGroupDao;
import com.gizwits.lease.device.entity.DeviceGroup;
import com.gizwits.lease.device.entity.DeviceGroupToDevice;
import com.gizwits.lease.device.entity.dto.DeviceGroupForAccessDto;
import com.gizwits.lease.device.entity.dto.DeviceGroupForAddDto;
import com.gizwits.lease.device.entity.dto.DeviceGroupForDetailDto;
import com.gizwits.lease.device.entity.dto.DeviceGroupForListDto;
import com.gizwits.lease.device.entity.dto.DeviceGroupForQueryDto;
import com.gizwits.lease.device.entity.dto.DeviceGroupForUpdateDto;
import com.gizwits.lease.device.service.DeviceGroupService;
import com.gizwits.lease.device.service.DeviceGroupToDeviceService;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.model.DeleteDto;
import io.swagger.models.auth.In;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 设备组 服务实现类
 * </p>
 *
 * @author lilh
 * @since 2017-08-15
 */
@Service
public class DeviceGroupServiceImpl extends ServiceImpl<DeviceGroupDao, DeviceGroup> implements DeviceGroupService {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DeviceGroupToDeviceService deviceGroupToDeviceService;

    @Override
    public boolean add(DeviceGroupForAddDto dto) {
        List<String> deviceIds = excludeInGroupDeviceIds(dto.getDeviceIds());
        //1.添加设备分组
        DeviceGroup deviceGroup = addDeviceGroup(dto);
        //2.添加关联表信息
        return addDeviceGroupToDevice(deviceIds, deviceGroup);
    }

    @Override
    public Page<DeviceGroupForListDto> page(Pageable<DeviceGroupForQueryDto> pageable) {
        initIfNecessary(pageable);
        Page<DeviceGroup> page = new Page<>();
        BeanUtils.copyProperties(pageable, page);
        Page<DeviceGroup> selectPage = selectPage(page, QueryResolverUtils.parse(pageable.getQuery(), new EntityWrapper<>()));
        Page<DeviceGroupForListDto> result = new Page<>();
        BeanUtils.copyProperties(selectPage, result);
        if (CollectionUtils.isNotEmpty(selectPage.getRecords())) {
            result.setRecords(selectPage.getRecords().stream().map(item -> {
                DeviceGroupForListDto dto = new DeviceGroupForListDto(item);
                dto.setOwnerName(item.getAssignedName());
                List<DeviceGroupToDevice> deviceGroupToDevices = deviceGroupToDeviceService.selectList(new EntityWrapper<DeviceGroupToDevice>().eq("device_group_id", item.getId()));
                dto.setDeviceCount(deviceGroupToDevices.size());
                dto.setDeviceIds(deviceGroupToDevices.stream().map(DeviceGroupToDevice::getDeviceSno).collect(Collectors.toList()));
                return dto;
            }).collect(Collectors.toList()));
        }
        return result;
    }

    @Override
    public boolean update(DeviceGroupForUpdateDto dto) {
        //1.更新设备组
        valid(dto);
        DeviceGroup deviceGroup = resolveDeviceGroup(dto);
        deviceGroup.setUtime(new Date());
        deviceGroup.setName(dto.getName());
        updateById(deviceGroup);
        //2.更新设备的分组信息
        //剔除已经在设备组中的设备，且不是当前组，且是有权限访问的
        List<String> deviceInAllGroup = resolveDeviceAlreadyInGroup();
        List<String> deviceInCurrentGroup = deviceGroupToDeviceService.selectList(new EntityWrapper<DeviceGroupToDevice>().eq("device_group_id", deviceGroup.getId()))
                .stream().map(DeviceGroupToDevice::getDeviceSno).collect(Collectors.toList());
        List<String> deviceNeedToExclude = deviceInAllGroup.stream().filter(item -> !deviceInCurrentGroup.contains(item)).collect(Collectors.toList());
        List<String> fromFront = dto.getDeviceIds().stream().filter(item -> !deviceNeedToExclude.contains(item)).collect(Collectors.toList());

        List<String> toDel = deviceInCurrentGroup.stream().filter(item -> !fromFront.contains(item)).collect(Collectors.toList());
        List<String> toAdd = fromFront.stream().filter(item -> !deviceInCurrentGroup.contains(item)).collect(Collectors.toList());
        if(CollectionUtils.isNotEmpty(toDel)) {
            deviceGroupToDeviceService.delete(new EntityWrapper<DeviceGroupToDevice>().eq("device_group_id", deviceGroup.getId()).in("device_sno", toDel));
        }
        if(CollectionUtils.isNotEmpty(toAdd)) {
            addDeviceGroupToDevice(toAdd, deviceGroup);
        }
        return true;
    }

    @Override
    public DeviceGroupForDetailDto detail(Integer id) {
        DeviceGroupForAccessDto dto = initAccessDto(id);
        DeviceGroup deviceGroup = selectOne(QueryResolverUtils.parse(dto, new EntityWrapper<>()));
        if (Objects.isNull(deviceGroup)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        DeviceGroupForDetailDto result = new DeviceGroupForDetailDto(deviceGroup);
        result.setOwnerName(deviceGroup.getAssignedName());
        result.setDevicesInGroup(deviceService.getDeviceByGroupId(id));
        return result;
    }

    private boolean addDeviceGroupToDevice(List<String> deviceIds, DeviceGroup deviceGroup) {
        List<DeviceGroupToDevice> deviceGroupToDevices = deviceIds.stream().map(sno -> {
            DeviceGroupToDevice deviceGroupToDevice = new DeviceGroupToDevice();
            deviceGroupToDevice.setCtime(new Date());
            deviceGroupToDevice.setUtime(new Date());
            deviceGroupToDevice.setDeviceGroupId(deviceGroup.getId());
            deviceGroupToDevice.setDeviceSno(sno);
            return deviceGroupToDevice;
        }).collect(Collectors.toList());
        return deviceGroupToDeviceService.insertBatch(deviceGroupToDevices);
    }

    private void valid(DeviceGroupForUpdateDto dto) {
        if (selectCount(new EntityWrapper<DeviceGroup>().eq("name", dto.getName()).ne("id", dto.getId())) > 0) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_GROUP_NAME_DUP);
        }
    }


    private DeviceGroupForAccessDto initAccessDto(Integer id) {
        if (Objects.isNull(id)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        DeviceGroupForAccessDto dto = new DeviceGroupForAccessDto();
        dto.setId(id);
        dto.setAccessableUserIds(sysUserService.resolveOwnerAccessableUserIds(sysUserService.getCurrentUserOwner()));
        return dto;
    }

    private DeviceGroup resolveDeviceGroup(DeviceGroupForUpdateDto dto) {
        if (Objects.isNull(dto.getId())) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        DeviceGroup deviceGroup = selectById(dto.getId());
        if (Objects.isNull(deviceGroup)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        return deviceGroup;
    }

    @Override
    public DeviceGroup selectById(Integer id){
        return selectOne(new EntityWrapper<DeviceGroup>().eq("id",id).eq("is_deleted",DeleteStatus.NOT_DELETED.getCode()));
    }

    private void initIfNecessary(Pageable<DeviceGroupForQueryDto> pageable) {
        if (Objects.isNull(pageable.getQuery())) {
            pageable.setQuery(new DeviceGroupForQueryDto());
        }
        //仅列出当前人创建的设备组数据
        pageable.getQuery().setAccessableUserIds(sysUserService.resolveSysUserAllSubAdminIds(sysUserService.getCurrentUserOwner()));
    }

    private List<String> excludeInGroupDeviceIds(List<String> deviceIds) {
        //1.剔除已经分组的设备
        List<String> excludeDeviceIds = resolveDeviceAlreadyInGroup();
        List<String> remainDeviceIds = deviceIds.stream().filter(item -> !excludeDeviceIds.contains(item)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(remainDeviceIds)) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_DONT_EXISTS);
        }
        return remainDeviceIds;
    }

    private DeviceGroup addDeviceGroup(DeviceGroupForAddDto dto) {
        //名称不能重复
        if (selectCount(new EntityWrapper<DeviceGroup>().eq("name", dto.getName())) > 0) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_GROUP_NAME_DUP);
        }
        DeviceGroup deviceGroup = new DeviceGroup();
        deviceGroup.setName(dto.getName());
        deviceGroup.setCtime(new Date());
        deviceGroup.setUtime(deviceGroup.getCtime());
        SysUser userOwner = sysUserService.getCurrentUserOwner();
        deviceGroup.setSysUserId(userOwner.getId());
        deviceGroup.setSysUserName(userOwner.getUsername());
        insert(deviceGroup);
        return deviceGroup;
    }

    /**
     * 获取已经当前用户已经分组的设备
     */
    @Override
    public List<String> resolveDeviceAlreadyInGroup() {
        //1.获取当前用户创建的所有设备组
        List<DeviceGroup> deviceGroups = selectList(new EntityWrapper<DeviceGroup>().in("sys_user_id", sysUserService.resolveSysUserAllSubAdminIds(sysUserService.getCurrentUserOwner())).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        if (CollectionUtils.isNotEmpty(deviceGroups)) {
            List<DeviceGroupToDevice> deviceGroupToDevices = deviceGroupToDeviceService.selectList(new EntityWrapper<DeviceGroupToDevice>().in("device_group_id", deviceGroups.stream().map(DeviceGroup::getId).collect(Collectors.toList())));
            if (CollectionUtils.isNotEmpty(deviceGroupToDevices)) {
                return deviceGroupToDevices.stream().map(DeviceGroupToDevice::getDeviceSno).collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

    @Override
    public String delete(List<Integer> ids){
        SysUser current = sysUserService.getCurrentUserOwner();
        List<Integer> userIds = sysUserService.resolveSysUserAllSubAdminIds(current);
        List<String> fails = new LinkedList<>();
        StringBuilder sb = new StringBuilder();
        List<DeviceGroup> list = selectList(new EntityWrapper<DeviceGroup>().in("id",ids).eq("is_deleted",DeleteStatus.NOT_DELETED.getCode()));
        for (DeviceGroup deviceGroup:list){
            if (userIds.contains(deviceGroup.getSysUserId())) {
                if (deviceGroup.getAssignedAccountId() != null || deviceGroup.getAssignedName() != null) {
                    fails.add(deviceGroup.getName());
                } else {
                    deviceGroup.setIsDeleted(DeleteStatus.DELETED.getCode());
                    deviceGroup.setUtime(new Date());
                    updateById(deviceGroup);
                    EntityWrapper<DeviceGroupToDevice> entityWrapper = new EntityWrapper<>();
                    entityWrapper.eq("device_group_id", deviceGroup.getId());
                    DeviceGroupToDevice deviceGroupToDevice = new DeviceGroupToDevice();
                    deviceGroupToDevice.setIsDeleted(DeleteStatus.DELETED.getCode());
                    deviceGroupToDevice.setUtime(new Date());
                    deviceGroupToDeviceService.update(deviceGroupToDevice, entityWrapper);
                }
            }else{
                fails.add(deviceGroup.getName());
            }
        }
        switch (fails.size()) {
            case 0:
                sb.append("删除成功");
                break;
            case 1:
                sb.append("您欲删除的设备组[" + fails.get(0) + "]已有归属，请先解绑设备组。");
                break;
            case 2:
                sb.append("您欲删除的设备组[" + fails.get(0) + "],[" + fails.get(1) + "]已有归属，请先解绑设备组。");
                break;
            default:
                sb.append("您欲删除的设备组[" + fails.get(0) + "],[" + fails.get(1) + "]等已有归属，请先解绑设备组。");
                break;
        }
        return sb.toString();
    }
}
