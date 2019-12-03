package com.gizwits.lease.device.service.impl;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.exceptions.SysExceptionEnum;
import com.gizwits.boot.exceptions.SystemException;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysRoleService;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.*;
import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.device.dao.DeviceLaunchAreaDao;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.DeviceLaunchArea;
import com.gizwits.lease.device.entity.dto.*;
import com.gizwits.lease.device.service.DeviceLaunchAreaService;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.device.vo.DeviceLaunchAreaCountVo;
import com.gizwits.lease.event.NameModifyEvent;
import com.gizwits.lease.event.OperatorStatusChangeEvent;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.manager.dto.AgentForUpdateDto;
import com.gizwits.lease.manager.entity.Agent;
import com.gizwits.lease.manager.entity.Operator;
import com.gizwits.lease.product.service.ProductDataPointService;
import com.gizwits.lease.user.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import sun.management.resources.agent;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;

/**
 * <p>
 * 设备投放点表 服务实现类
 * </p>
 *
 * @author yinhui
 * @since 2017-07-12
 */
@Service
public class DeviceLaunchAreaServiceImpl extends ServiceImpl<DeviceLaunchAreaDao, DeviceLaunchArea> implements DeviceLaunchAreaService {

    protected Logger logger = LoggerFactory.getLogger("LEASE_LOGGER");

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private SysRoleService sysRoleService;



    @Override
    public boolean addDeviceLaunchArea(DeviceLaunchArea deviceLaunchArea) {
        SysUser sysUser = sysUserService.getCurrentUserOwner();
        deviceLaunchArea.setSysUserId(sysUser.getId());
        deviceLaunchArea.setSysUserName(sysUser.getUsername());
        deviceLaunchArea.setOwnerId(sysUser.getId());
        deviceLaunchArea.setUtime(new Date());
        deviceLaunchArea.setCtime(new Date());
        String name = deviceLaunchArea.getName();
        if (ParamUtil.isNullOrEmptyOrZero(name)) {
            throw new SystemException(SysExceptionEnum.ILLEGAL_PARAM.getCode(), SysExceptionEnum.ILLEGAL_PARAM.getMessage());
        }

        return insert(deviceLaunchArea);
    }

    private String getPictureUrl(MultipartFile file) {
        String pictureUrl = "";
        if (file != null) {
            try {
                if (!file.getOriginalFilename().contains(".")) {
                    LeaseException.throwSystemException(LeaseExceEnums.PICTURE_SUFFIX_ERROR);
                }
                if (!PictureUtils.checkFile(file.getOriginalFilename())) {
                    LeaseException.throwSystemException(LeaseExceEnums.PICTURE_SUFFIX_ERROR);
                }
                String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
                String newFilename = UUID.randomUUID() + suffix;

                //这里暂时注释掉
//                File newFile = new File(SysConfigUtils.get(CommonSystemConfig.class).getLaunchAreaImagePath() + newFilename);
                File newFile = new File("G:\\data\\lease\\files\\launch-area\\images\\" + newFilename);
                File dir = newFile.getParentFile();
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                //这里暂时注释掉
                file.transferTo(newFile);
                pictureUrl = SysConfigUtils.get(CommonSystemConfig.class).getLaunchAreaImageURL() + newFilename;

            } catch (Exception ex) {
                logger.error("==============>图片上传失败，原因：" + ex);
                ex.printStackTrace();
                LeaseException.throwSystemException(LeaseExceEnums.UPLOAD_ERROR);
            }
        }
        return pictureUrl;
    }

    @Override
    public String deleteDeviceLaunchAreas(List<Integer> areaId) {
        SysUser current = sysUserService.getCurrentUserOwner();
        List<Integer> userIds = sysUserService.resolveSysUserAllSubAdminIds(current);
        List<String> fails = new LinkedList<>();
        StringBuffer sb = new StringBuffer();
        List<DeviceLaunchArea> deviceLaunchAreas = selectList(new EntityWrapper<DeviceLaunchArea>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).in("id", areaId));
        for (DeviceLaunchArea deviceLaunchArea : deviceLaunchAreas) {
            //判断是否拥有删除投放点的权限，能删除在列表看到的所有数据，防止调用接口乱删
            if (userIds.contains(deviceLaunchArea.getSysUserId())) {
                EntityWrapper<Device> entityWrapper1 = new EntityWrapper<>();
                entityWrapper1.eq("launch_area_id", deviceLaunchArea.getId()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
                if (deviceService.selectCount(entityWrapper1) <= 0) {
                    deviceLaunchArea.setIsDelete(DeleteStatus.DELETED.getCode());
                    deviceLaunchArea.setUtime(new Date());
                    updateById(deviceLaunchArea);

                } else {
                    fails.add(deviceLaunchArea.getName());
                }
            } else {
                fails.add(deviceLaunchArea.getName());
            }

        }
        switch (fails.size()) {
            case 0:
                sb.append("删除成功");
                break;
            case 1:
                sb.append("您欲删除的仓库[" + fails.get(0) + "]已有设备，请先转移设备并重试。");
                break;
            case 2:
                sb.append("您欲删除的仓库[" + fails.get(0) + "],[" + fails.get(1) + "]已有设备，请先转移设备并重试。");
                break;
            default:
                sb.append("您欲删除的仓库[" + fails.get(0) + "],[" + fails.get(1) + "]等已有设备，请先转移设备并重试。");
                break;
        }
        return sb.toString();
    }


    @Override
    public DeviceLaunchArea selectById(Integer id) {
        return selectOne(new EntityWrapper<DeviceLaunchArea>().eq("id", id).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }

    @Override
    public boolean updateDeviceLaunchArea( DeviceLaunchArea deviceLaunchArea) {
        DeviceLaunchArea oldLaunch = getLaunchAreaInfoById(deviceLaunchArea.getId());
        if (ParamUtil.isNullOrEmptyOrZero(oldLaunch)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        String oldName = oldLaunch.getName();
        deviceLaunchArea.setUtime(new Date());
        String name = deviceLaunchArea.getName();
        if (ParamUtil.isNullOrEmptyOrZero(name)) {
            throw new SystemException(SysExceptionEnum.ILLEGAL_PARAM.getCode(), SysExceptionEnum.ILLEGAL_PARAM.getMessage());
        }

        boolean flag = updateById(deviceLaunchArea);
        if (!oldName.equals(name)) {
            NameModifyEvent<Integer> nameModifyEvent = new NameModifyEvent<Integer>(oldLaunch, oldLaunch.getId(), oldName, name);
            publishEvent(nameModifyEvent);
        }
        return flag;
    }

    private void publishEvent(NameModifyEvent<Integer> nameModifyEvent) {
        CommonEventPublisherUtils.publishEvent(nameModifyEvent);
    }

    public Page<DeviceLaunchAreaListDto> getDeviceLaunchAreaListPage(Pageable<DeviceLaunchAreaQueryDto> pageable) {

        return getDeviceLaunchAreaListDtoPage(pageable, new Page<>(), new EntityWrapper<>());
    }

    @Override
    public Page<DeviceLaunchAreaListDto> getUnAllotDeviceLaunchAreaListPage(Pageable<DeviceLaunchAreaQueryDto> pageable) {
        Page<DeviceLaunchArea> page = new Page<>();
//        page.setOrderByField("utime");
        List<Integer> userIds = sysUserService.resolveSysUserAllSubAdminIds(sysUserService.getCurrentUserOwner());
        EntityWrapper<DeviceLaunchArea> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("status", 1)
                .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode())
                .in("owner_id", userIds);
        entityWrapper.orderBy("utime", false);
        BeanUtils.copyProperties(pageable, page);
        Page<DeviceLaunchArea> page1 = selectPage(page, entityWrapper);

        Page<DeviceLaunchAreaListDto> result = new Page<>();
        BeanUtils.copyProperties(page1, result);

        List<DeviceLaunchAreaListDto> deviceLaunchAreaListDtoList = new ArrayList<>();
        for (DeviceLaunchArea dla : page1.getRecords()) {
            DeviceLaunchAreaListDto deviceLaunchAreaListDto = new DeviceLaunchAreaListDto();
            deviceLaunchAreaListDto.setDeviceLaunchArea(dla);
            Integer count = deviceService.countDeviceByDeviceLaunchAreaId(dla.getId());
            deviceLaunchAreaListDto.setDeviceCount(count);
            deviceLaunchAreaListDtoList.add(deviceLaunchAreaListDto);
        }
        result.setRecords(deviceLaunchAreaListDtoList);
        return result;
    }

    private Page<DeviceLaunchAreaListDto> getDeviceLaunchAreaListDtoPage(Pageable<DeviceLaunchAreaQueryDto> pageable, Page<DeviceLaunchArea> page
            , EntityWrapper<DeviceLaunchArea> wrapper) {
        BeanUtils.copyProperties(pageable, page);


        //修改时间
        if (pageable.getQuery().getUpTimeStart()!=null && pageable.getQuery().getUpTimeEnd()!=null){
            wrapper.between("utime",pageable.getQuery().getUpTimeStart(),pageable.getQuery().getUpTimeEnd());
        }
        Page<DeviceLaunchArea> deviceLaunchAreas = selectPage(page, QueryResolverUtils.parse(pageable.getQuery(), wrapper));


        Page<DeviceLaunchAreaListDto> result = new Page<>();
        BeanUtils.copyProperties(pageable, result);
        List<Integer> userIds = sysUserService.resolveSysUserAllSubAdminIds(sysUserService.getCurrentUserOwner());
        List<DeviceLaunchAreaListDto> deviceLaunchAreaListDtoList = new ArrayList<>();
        for (DeviceLaunchArea dla : deviceLaunchAreas.getRecords()) {
            DeviceLaunchAreaListDto deviceLaunchAreaListDto = new DeviceLaunchAreaListDto();
            deviceLaunchAreaListDto.setDeviceLaunchArea(dla);
//            Integer count = deviceService.countDeviceByDeviceLaunchAreaId(dla.getId());
//            deviceLaunchAreaListDto.setDeviceCount(count);
            deviceLaunchAreaListDto.setCurrentOwner(deviceService.resolveOwner(dla.getOwnerId()));
            if (userIds.contains(dla.getOwnerId())) {
                deviceLaunchAreaListDto.setCanModify(true);
            }
            deviceLaunchAreaListDtoList.add(deviceLaunchAreaListDto);
        }
        result.setRecords(deviceLaunchAreaListDtoList);
        result.setTotal(deviceLaunchAreas.getTotal());
        result.setCurrent(pageable.getCurrent());
        return result;
    }

    /**
     *   if (CollectionUtils.isNotEmpty(deviceLaunchAreas.getRecords())) {
     *             result.setRecords(new ArrayList<>(deviceLaunchAreas.getRecords().size()));
     *             deviceLaunchAreas.getRecords().forEach(item -> {
     *
     *                 DeviceLaunchAreaListDto dto = new DeviceLaunchAreaListDto(item);
     *
     *                 result.getRecords().add(dto);
     *                 result.setTotal(deviceLaunchAreas.getTotal());
     *                 result.setCurrent(pageable.getCurrent());
     *             });
     *         }
     * @param name
     * @return
     */

    @Override

    public boolean deviceLaunchAreaIsExist(String name) {
        EntityWrapper<DeviceLaunchArea> entityWrapper = new EntityWrapper<>();
        entityWrapper.in("sys_user_id", sysUserService.resolveOwnerAccessableUserIds(sysUserService.getCurrentUser()))
                .eq("name", name)
                .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode())
                .eq("status", 1);
        DeviceLaunchArea deviceLaunchArea = selectOne(entityWrapper);
        return !ParamUtil.isNullOrEmptyOrZero(deviceLaunchArea);
    }

    public DeviceLaunchArea getLaunchAreaInfoById(Integer launchaAreaId) {
        EntityWrapper<DeviceLaunchArea> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("id", launchaAreaId).eq("is_deleted", 0);
        return selectOne(entityWrapper);
    }


    @Override
    public boolean checkIfBinded(Integer id) {
        DeviceLaunchArea deviceLaunchArea = getLaunchAreaInfoById(id);
        if (Objects.isNull(deviceLaunchArea)) {
            return false;
        }
        return ParamUtil.isNullOrEmptyOrZero(deviceLaunchArea.getOperatorId());
    }

    @Override
    public boolean change(DeviceLaunchAreaQueryDto dto) {
        assertNotNull(dto.getIds());
        boolean result = false;
        for (Integer areaId : dto.getIds()) {
            DeviceLaunchArea deviceLaunchArea = selectById(areaId);
            if (Objects.isNull(deviceLaunchArea)) {
                LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
            }
            deviceLaunchArea.setStatus(dto.getStatus());
            deviceLaunchArea.setUtime(new Date());
            result = updateById(deviceLaunchArea);
        }
        return result;
    }

    @Override
    public Pageable<DeviceLaunchAreaQueryDto> setParam(Pageable<QueryForCreatorDto> data) {
        Pageable<DeviceLaunchAreaQueryDto> pageable = new Pageable<>();
        BeanUtils.copyProperties(data, pageable, "query");
        pageable.setQuery(new DeviceLaunchAreaQueryDto());
        if (Objects.isNull(data.getQuery()) || Objects.isNull(data.getQuery().getCreatorId())) {
            LeaseException.throwSystemException(LeaseExceEnums.PARAMS_ERROR);
        }
        pageable.getQuery().setAccessableUserIds(Collections.singletonList(data.getQuery().getCreatorId()));
        return pageable;
    }

    @Override
    public MJDeviceLaunchAreaDetail detail(Integer launchAreaId) {
        DeviceLaunchArea launchArea = getLaunchAreaInfoById(launchAreaId);
        if (Objects.isNull(launchArea)) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_LAUNCH_AREA_NOT_EXIST);
        }
        MJDeviceLaunchAreaDetail deviceLaunchAreaDetail = new MJDeviceLaunchAreaDetail(launchArea);
        List<Integer> userIds = sysUserService.resolveOwnerAccessableUserIds(sysUserService.getCurrentUserOwner());
        if (userIds.contains(launchArea.getSysUserId())) {
            deviceLaunchAreaDetail.setIsModify(1);
        }
        return deviceLaunchAreaDetail;
    }

    @Override
    public List<DeviceMaintainerDto> getDeviceMaintainerDtos() {
        SysUser sysUser = sysUserService.getCurrentUserOwner();
        List<DeviceMaintainerDto> deviceMaintainers = new ArrayList<>();
        // 找到维护人员角色id
        Integer roleId = sysRoleService.getRoleIdBySysUserId(sysUser.getId());
        if (!ParamUtil.isNullOrEmptyOrZero(roleId)) {
            List<SysUser> maintainUsers = sysUserService.getUsersByRoleId(roleId);
            for (SysUser user : maintainUsers) {
                DeviceMaintainerDto deviceMaintainerDto = new DeviceMaintainerDto();
                deviceMaintainerDto.setId(user.getId());
                deviceMaintainerDto.setName(user.getUsername());
                deviceMaintainers.add(deviceMaintainerDto);
            }
        }
        return deviceMaintainers;
    }

    @Override
    public List<DeviceLaunchAreaCountVo> areaListManager() {
        SysUser user = sysUserService.getCurrentUser();
        logger.info("维护人员id=" + user.getId());
        EntityWrapper<DeviceLaunchArea> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("maintainer_id", user.getId()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        List<DeviceLaunchAreaCountVo> countVos = new ArrayList<>();
        selectList(entityWrapper).forEach(item -> {
            DeviceLaunchAreaCountVo countVo = new DeviceLaunchAreaCountVo();
            countVo.setDeviceLaunchAreaId(item.getId());
            countVo.setAreaName(item.getName());
            countVo.setDeviceCount(countDevice(item.getId()));
            countVos.add(countVo);
        });
        return countVos;
    }

    private Integer countDevice(Integer deviceLaunchAreaId) {
        return deviceService.selectCount(new EntityWrapper<Device>().eq("launch_area_id", deviceLaunchAreaId).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }

    @Override
    public List<String> getDeviceSnoByMaintainerId(Integer maintainerId) {
        EntityWrapper<DeviceLaunchArea> entityWrapper = new EntityWrapper<>();

        entityWrapper.eq("maintainer_id", maintainerId).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        List<Integer> ids = selectList(entityWrapper).stream().map(DeviceLaunchArea::getId).collect(Collectors.toList());
        List<String> sno = new ArrayList<>();
        if (!ParamUtil.isNullOrEmptyOrZero(ids)) {
            sno = deviceService.selectList(new EntityWrapper<Device>().in("launch_area_id", ids).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()))
                    .stream().map(Device::getSno).collect(Collectors.toList());

        }
        return sno;
    }

    @Override
    public List<DeviceLaunchArea> getListByMaintainerId(Integer maintainerId) {
        Wrapper wrapper = new EntityWrapper<DeviceLaunchArea>();
        wrapper.eq("maintainer_id", maintainerId).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        return selectList(wrapper);
    }
}
