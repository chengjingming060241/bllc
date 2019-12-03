package com.gizwits.lease.device.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.DeviceToProductServiceMode;
import com.gizwits.lease.device.dao.DeviceToProductServiceModeDao;
import com.gizwits.lease.device.entity.dto.DeviceAddDto;
import com.gizwits.lease.device.entity.dto.DeviceAssignDto;
import com.gizwits.lease.device.entity.dto.DeviceAssignMoreModeDto;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.device.service.DeviceToProductServiceModeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.product.dto.ProductServiceDetailForDeviceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 设备收费模式表(多对多) 服务实现类
 * </p>
 *
 * @author yinhui
 * @since 2018-01-05
 */
@Service
public class DeviceToProductServiceModeServiceImpl extends ServiceImpl<DeviceToProductServiceModeDao, DeviceToProductServiceMode> implements DeviceToProductServiceModeService {
    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 设备批量分配收费模式
     *
     * @param deviceAssignDto
     */
    @Override
    @Transactional
    public void batchInsertBySno(DeviceAssignDto deviceAssignDto) {
        if (!ParamUtil.isNullOrEmptyOrZero(deviceAssignDto) && deviceAssignDto.getSno().size() != 0) {
            for (String sno : deviceAssignDto.getSno()) {
                DeviceAssignMoreModeDto assignDto = new DeviceAssignMoreModeDto();
                assignDto.setSno(sno);
                List<Integer> serviceModeIds = new ArrayList<>(1);
                serviceModeIds.add(deviceAssignDto.getAssignId());
                assignDto.setServiceModeId(serviceModeIds);
                batchInsertOrUpdate(assignDto);
            }
        }
    }


    /**
     * 设备添加/修改多个收费模式
     *
     * @param dto
     */
    @Override
    @Transactional
    public void batchInsertOrUpdate(DeviceAssignMoreModeDto dto) {
        // 先删除设备已有的收费模式
        deleteBySno(dto.getSno());
        // 插入新的收费
        if (!ParamUtil.isNullOrEmptyOrZero(dto.getServiceModeId())) {
            List<Integer> serviceModeIds = dto.getServiceModeId().stream().distinct().collect(Collectors.toList());
            SysUser sysUser = sysUserService.getCurrentUserOwner();
            Date now = new Date();
            for (Integer serviceModeId : serviceModeIds) {
                DeviceToProductServiceMode serviceMode = new DeviceToProductServiceMode();
                serviceMode.setCtime(now);
                serviceMode.setUtime(now);
                serviceMode.setServiceModeId(serviceModeId);
                serviceMode.setSno(dto.getSno());
                serviceMode.setSysUserId(sysUser.getId());
                serviceMode.setSysUserName(sysUser.getUsername());
                serviceMode.setIsDeleted(DeleteStatus.NOT_DELETED.getCode());
                insert(serviceMode);
            }
        }
    }

    /**
     * 删除设备的收费模式
     *
     * @param sno
     * @return
     */
    @Override
    public boolean deleteBySno(String sno) {
        if (ParamUtil.isNullOrEmptyOrZero(deviceService.getDeviceInfoBySno(sno))) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_DONT_EXISTS);
        }
        EntityWrapper<DeviceToProductServiceMode> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("sno", sno);
        DeviceToProductServiceMode serviceMode = new DeviceToProductServiceMode();
        serviceMode.setIsDeleted(DeleteStatus.DELETED.getCode());
        serviceMode.setUtime(new Date());
        return update(serviceMode, entityWrapper);
    }

    /**
     * 查找设备的收费模式id
     *
     * @param sno
     * @return
     */
    @Override
    public List<Integer> resolveServiceModeIdBySno(String sno) {
        EntityWrapper<DeviceToProductServiceMode> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("sno", sno).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        List<DeviceToProductServiceMode> list = selectList(entityWrapper);
        if (ParamUtil.isNullOrEmptyOrZero(list)) {
            return null;
        }
        List<Integer> modeIds = list.stream().map(DeviceToProductServiceMode::getServiceModeId).collect(Collectors.toList());
        return modeIds;
    }

    @Override
    public Integer countDeviceByServiceMode(Integer serviceMode) {
        Wrapper<DeviceToProductServiceMode> wrapper = new EntityWrapper();
        wrapper.eq("service_mode_id", serviceMode);
        wrapper.eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        return selectCount(wrapper);
    }

}
