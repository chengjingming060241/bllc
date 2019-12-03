package com.gizwits.lease.common.perm;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.CommonEventPublisherUtils;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.DeviceGroup;
import com.gizwits.lease.device.entity.DeviceGroupToDevice;
import com.gizwits.lease.device.entity.DeviceLaunchArea;
import com.gizwits.lease.device.entity.dto.DeviceAssignRecordDto;
import com.gizwits.lease.device.entity.dto.DeviceForAssignDto;
import com.gizwits.lease.device.entity.dto.DeviceForUnbindDto;
import com.gizwits.lease.device.service.DeviceGroupService;
import com.gizwits.lease.device.service.DeviceGroupToDeviceService;
import com.gizwits.lease.device.service.DeviceLaunchAreaService;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.device.service.DeviceServiceModeSettingService;
import com.gizwits.lease.enums.AssignDestinationType;


import com.gizwits.lease.event.DeviceAssignEvent;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.manager.entity.Agent;

import com.gizwits.lease.manager.service.AgentService;


import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author lilh
 * @date 2017/8/24 14:28
 */
public abstract class AbstractCommonRoleResolver implements CommonRoleResolver, InitializingBean {

    private Logger logger = LoggerFactory.getLogger("BENEFIT_LOGGER");
    @Autowired
    protected DeviceService deviceService;

    @Autowired
    protected SysUserService sysUserService;


    @Autowired
    private AgentService agentService;

    @Autowired
    private DeviceGroupService deviceGroupService;

    @Autowired
    private DeviceGroupToDeviceService deviceGroupToDeviceService;

    @Autowired
    private DeviceServiceModeSettingService deviceServiceModeSettingService;

    @Autowired
    private DeviceLaunchAreaService deviceLaunchAreaService;







    protected Map<AssignDestinationType, AccountResolver> map = new HashMap<>();

    @Override
    @Transactional
    public boolean assign(DeviceForAssignDto dto, SysUserRoleTypeHelper helper) {
        initDevice(dto);
        preAssign(dto, helper);
        return doAssign(dto, helper);
    }

    @Override
    @Transactional
    public boolean unbind(DeviceForUnbindDto unbindDto, SysUserRoleTypeHelper helper) {
        DeviceForAssignDto dto = new DeviceForAssignDto();
        BeanUtils.copyProperties(unbindDto, dto);
        initDevice(dto);
        preUnbind(dto, helper);
        return doUnbind(dto, helper);
    }

    protected boolean doUnbind(DeviceForAssignDto dto, SysUserRoleTypeHelper helper) {
        //解绑，回滚到原来
        //1.设备回滚
        if(!ParamUtil.isNullOrEmptyOrZero(dto.getDevices())) {
            List<DeviceAssignRecordDto> recordDtos = resolveRecord(dto.getDevices(), helper.getSysAccountId());
            dto.getDevices().forEach(item -> {
                //清空投放点和收费模式
                resetLaunchAreaAndServiceMode(item);
            });
            resetOwnerBatch(dto.getDevices(), helper.getSysAccountId(), helper.getOrigizationName());
            customUnBind(dto, helper);
            CommonEventPublisherUtils.publishEvent(new DeviceAssignEvent(recordDtos, DeviceAssignEvent.Type.UNBIND));
        }
        return true;
    }

    private void customUnBind(DeviceForAssignDto dto, SysUserRoleTypeHelper helper) {
        if (CollectionUtils.isNotEmpty(dto.getDeviceGroups())) {
            dto.getDeviceGroups().forEach(deviceGroup -> {
                //将设备组的当前归属置空，回到创建者手里
                deviceGroup.setAssignedAccountId(null);
                deviceGroup.setAssignedName(null);
                deviceGroup.setUtime(new Date());
                deviceGroupService.updateAllColumnById(deviceGroup);
            });
        }
        if (CollectionUtils.isNotEmpty(dto.getDevices())){
            dto.getDevices().forEach(item->{
                //将设备在设备分组中释放
                DeviceGroupToDevice deviceGroupToDevice =  deviceGroupToDeviceService.selectOne(new EntityWrapper<DeviceGroupToDevice>().eq("is_deleted",DeleteStatus.NOT_DELETED.getCode()).eq("device_sno",item.getSno()));
                if (Objects.nonNull(deviceGroupToDevice)){
                    deviceGroupToDevice.setUtime(new Date());
                    deviceGroupToDevice.setIsDeleted(DeleteStatus.DELETED.getCode());
                    deviceGroupToDeviceService.updateById(deviceGroupToDevice);
                }
            });
        }
    }

    /**（现在的流程无需做分润的操作，解绑时去除跨级限制）
     * 1.检查设备是不是在自己的直接下级
     * 2.为所有的设备执行分润规则(后面可能调整为按设备执行分润规则),同时将设备的分润规则从原来的分润规则中移除
     * 3.修改当前用户分润规则中设备对子级的分润比例为0
     * 4.执行解绑的的后续操作,清除设备的收费模式投放点,修改设备的归属
     * @param dto
     * @param helper
     */
    protected void preUnbind(DeviceForAssignDto dto, SysUserRoleTypeHelper helper) {
        SysUser currentUserOwner = getCurrentUserOwner(dto);
//        检验设备的当前拥有者是当前登录人创建的
        if(!ParamUtil.isNullOrEmptyOrZero(dto.getDevices())) {
            /** 判断设备是否归于该用户的下级 */
            List<Integer> directSubAdminIds = sysUserService.resolveSysUserAllSubIds(currentUserOwner);
            Set<Integer> directSubAdminIdsSet = new HashSet<>(directSubAdminIds);
            /** 移除当前登录用户 */
            directSubAdminIdsSet.remove(currentUserOwner.getId());
            for (Device device:dto.getDevices()){
                if (!directSubAdminIdsSet.contains(device.getOwnerId())){
                    LeaseException.throwSystemException(device.getSno() ,LeaseExceEnums.DEVICE_NOT_BELONG_DIRECT_USER);
                }
            }

//            /** 为解绑对象生成分润规则,同时删除解绑对象分润规则中的设备分润规则 */
//            logger.info("======preUnbind generateShareBenefitForShareRule for device:{}", dto.getDevices().get(0));
//            deviceService.generateShareSheet(dto.getDevices(), true);
//
//            /** 为当前用户执行分润规则 */
//            ShareBenefitRule currentLoginUserRule = shareBenefitRuleService.getRuleBySysAccountId(currentUserOwner.getId());
//            if (Objects.nonNull(currentLoginUserRule)){
//                logger.info("======preUnbind generateShareBenefitForShareRule for currentUser:{}",currentLoginUserRule.getId());
//                shareBenefitSheetService.generateShareBenefitForShareRule(currentLoginUserRule);
//                /** 修改当前用户的分润规则中相应设备的下级分润比例为0 */
//                for (Device device:dto.getDevices()){
//                    shareBenefitRuleDetailDeviceService.updateDeviceChildrenPercentage(device.getSno(),BigDecimal.ZERO,currentLoginUserRule.getId());
//                }
//            }

            //解绑时删除设备收费模式中的相关配置
            deviceServiceModeSettingService.deleteAssignDeviceServiceMode(dto.getDevices());
        }
    }

    protected boolean doAssign(DeviceForAssignDto dto, SysUserRoleTypeHelper helper) {
        //1.获取运营商或代理商关联的系统帐号
        Integer sysAccountId = dto.getResolvedAccountId();
        if (sysAccountId == null) {
            resolveAccountId(dto);
        }
        if (Objects.isNull(sysAccountId)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }

        //生成分润单
//        logger.info("======doAssign generateShareBenefitForShareRule for device:{}", dto.getDevices().get(0));
//        deviceService.generateShareSheet(dto.getDevices(), false);
        //分配
        List<DeviceAssignRecordDto> recordDtos = resolveRecord(dto.getDevices(), sysAccountId);
        // 无需晴空设备已分配的收费模式和投放点
//        dto.getDevices().forEach(item -> {
//            //在清空收费模式前,记录设备的收费模式
//            recordDeviceServiceModeSetting(item, sysAccountId);
//            //清空投放点和收费模式
//            resetLaunchAreaAndServiceMode(item);
//        });
        resetOwnerBatch(dto.getDevices(), sysAccountId, dto.getAssignedName());
        //deviceService.updateBatchById(dto.getDevices());
        dto.setResolvedAccountId(sysAccountId);
        customAssign(dto, helper);
        recordDtos.forEach(item -> {
            item.setIsAgent(dto.getIsAgent());
            item.setIsOperator(dto.getIsOperator());
        });
        CommonEventPublisherUtils.publishEvent(new DeviceAssignEvent(recordDtos, DeviceAssignEvent.Type.ASSIGN));
        return true;
    }

    private void recordDeviceServiceModeSetting(Device device, Integer assignAccountId) {
        deviceServiceModeSettingService.saveDeviceServiceMode(device.getSno(), device.getOwnerId(), assignAccountId);
    }

    //做一些检验

    /**
     * 分配设备无需生成分润单
     * @param dto
     * @param helper
     */
    protected void preAssign(DeviceForAssignDto dto, SysUserRoleTypeHelper helper) {
        //若设备属于自己，则可分配,仅供厂商和代理商使用，运营商可单独实现
        boolean has = dto.getDevices().stream().anyMatch(item -> !Objects.equals(item.getOwnerId(), helper.getSysAccountId()));
        if (has) {
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_CANNOT_ASSIGN);
        }
        if(dto.getResolvedAccountId() == null) {
            fillAssignSysAccountId(dto);
        }
//        for (String sno:dto.getDeviceSnos()){
//            OrderBase usingOrder = orderBaseService.getDeviceLastOrderByStatus(sno, OrderStatus.USING.getCode());
//            if (Objects.nonNull(usingOrder)){
//                LeaseException.throwSystemException(sno, LeaseExceEnums.HAS_UNFINISH_ORDER ,usingOrder.getOrderNo());
//            }
//        }

        //检测设备是否具有分配规则,,为当前登录用户生成分润规则
//        if (helper.isOperator() || helper.isAgent()) {
//            List<ShareBenefitRuleDeviceVo> deviceShareBenefitRuleList = shareBenefitRuleDetailDeviceService.getDeviceShareRuleByRuleIdOrSysAccountIdAndSno(null,helper.getSysAccountId(),dto.getDeviceSnos());
//            if (CollectionUtils.isNotEmpty(deviceShareBenefitRuleList)){
//                /**
//                 * 循环检查设备是否有使用中订单,如果有使用中订单则不能分配设置,,因为订单需要在当前用户身上完结分润才行
//                 */
//                for (ShareBenefitRuleDeviceVo deviceVo:deviceShareBenefitRuleList){
//                    OrderBase usingOrder = orderBaseService.getDeviceLastOrderByStatus(deviceVo.getSno(), OrderStatus.USING.getCode());
//                    if (Objects.nonNull(usingOrder)){
//                        LeaseException.throwSystemException(deviceVo.getSno(), LeaseExceEnums.HAS_UNFINISH_ORDER ,usingOrder.getOrderNo());
//                    }
//                }
//                /**
//                 * 因为分配的设备中包含在了当前用户的规则中,需要执行一次分润将订单完结掉
//                 */
//                logger.info("======preAssign generateShareBenefitForShareRule :{}", helper.getSysAccountId());
//                shareBenefitSheetService.generateShareBenefitForShareRule(shareBenefitRuleService.getRuleBySysAccountId(helper.getSysAccountId()));
//            }

//            dto.getDevices().forEach(item -> {
//                /**
//                 * 如果这些设备在当前用户的分润规则中不存在,,那么不需要将设备添加到自己的规则中
//                 * 如果这些设备在分润规则中存在,,那么需要执行一次分润规则,将之前的订单结算一下
//                 */
//                ShareBenefitRuleDeviceVo rule = shareBenefitRuleDetailDeviceService.getDeviceShareRuleBySysAccountId(item.getSno(), helper.getSysAccountId());
//                if (ParamUtil.isNullOrEmptyOrZero(rule)) {
//                    saveDeviceShareRuleForOperator(dto, item, helper.getSysAccountId());
//                }else{//有设备在当前用户的分润规则中,执行分润规则
//                    //shareBenefitSheetService.generateShareBenefitForShareRule(shareBenefitRuleService.selectById(rule.getRuleId()));
//                }
//            });
//        }
        /**
         * 分配设备到下级,不用为下级创建分润规则,可以手动为下级创建分润规则,或者下级将设备继续往下分配时创建
         */
//        if (!dto.getAssignDestinationType().equals(AssignDestinationType.LAUNCH_AREA.getCode())){
//            dto.getDevices().forEach(item -> {
//                ShareBenefitRuleDeviceVo assignRule = shareBenefitRuleDetailDeviceService.getDeviceShareRuleBySysAccountId(item.getSno(), dto.getAssignedId());
//                if (ParamUtil.isNullOrEmptyOrZero(assignRule)) {
//                    saveDeviceShareRuleForOperator(dto, item, dto.getResolvedAccountId());
//                }
//            });
//        }
    }

    private void fillAssignSysAccountId(DeviceForAssignDto dto){
        if (dto.getAssignDestinationType().equals(AssignDestinationType.AGENT.getCode())){
            Agent agent = agentService.selectById(dto.getAssignedId());
            if (Objects.nonNull(agent)){
                dto.setResolvedAccountId(agent.getSysAccountId());
                dto.setAssignedName(agent.getName());
            }
        }

        if (dto.getAssignDestinationType().equals(AssignDestinationType.LAUNCH_AREA.getCode())){
            DeviceLaunchArea launchArea = deviceLaunchAreaService.selectById(dto.getAssignedId());
            if (Objects.nonNull(launchArea)){
                dto.setResolvedAccountId(launchArea.getSysUserId());
            }
        }
    }



    protected void customAssign(DeviceForAssignDto dto, SysUserRoleTypeHelper helper) {
        if (CollectionUtils.isNotEmpty(dto.getDeviceGroups())) {//设备组分配
            dto.getDeviceGroups().forEach(deviceGroup -> {
                deviceGroup.setAssignedAccountId(dto.getResolvedAccountId());
                deviceGroup.setAssignedName(dto.getAssignedName());
                deviceGroup.setUtime(new Date());
            });
            deviceGroupService.updateBatchById(dto.getDeviceGroups());
        }
    }

    protected Integer resolveAccountId(DeviceForAssignDto dto) {
        AccountResolver resolver = map.get(AssignDestinationType.getType(dto.getAssignDestinationType()));
        Integer result = null;
        if (Objects.nonNull(resolver)) {
            result = resolver.resolve(dto);
        }
        return result;
    }

    private void resetOwnerBatch(List<Device> deviceList, Integer ownerId, String ownerName){
        deviceList.forEach(item -> {
            item.setOwnerId(ownerId);
            item.setOwnerName(ownerName);
            item.setUtime(new Date());

            deviceService.updateAllColumnById(item);
        });
        //deviceService.updateBatchById(deviceList);
    }

    private void resetLaunchAreaAndServiceMode(Device item) {
        item.setLaunchAreaId(null);
        item.setLaunchAreaName(null);
        item.setServiceId(null);
        item.setServiceName(null);
    }

    private List<DeviceAssignRecordDto> resolveRecord(List<Device> devices, Integer sysAccountId) {
        SysUser current = sysUserService.getCurrentUser();
        if(ParamUtil.isNullOrEmptyOrZero(devices)){
            return null;
        }
        return devices.stream().map(item -> {
            DeviceAssignRecordDto dto = new DeviceAssignRecordDto();
            dto.setSourceOperator(item.getOwnerId());
            dto.setDestinationOperator(sysAccountId);
            dto.setMac(item.getMac());
            dto.setSno(item.getSno());
            dto.setCurrent(current);
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     *解绑时无需判断是否时直系上级
     * @param dto
     */
    private void initDevice(DeviceForAssignDto dto) {
        SysUser currentUserOwner = getCurrentUserOwner(dto);
        if (CollectionUtils.isEmpty(dto.getDevices())) {
            List<Device> devices = null;
            if (CollectionUtils.isNotEmpty(dto.getDeviceGroupIds())) {//设备组
                //1.检验设备组的有效性：存在
                List<DeviceGroup> deviceGroups = deviceGroupService.selectList(new EntityWrapper<DeviceGroup>().in("id", dto.getDeviceGroupIds()).in("sys_user_id",sysUserService.resolveSysUserAllSubIds(currentUserOwner)));
                if (CollectionUtils.isEmpty(deviceGroups) || deviceGroups.size() != dto.getDeviceGroupIds().size()) {
                    LeaseException.throwSystemException(LeaseExceEnums.DEVICE_CANNOT_ACCESS_GROUP);
                }
                dto.setDeviceGroups(deviceGroups);
                //2.查询设备
                List<DeviceGroupToDevice> deviceGroupToDevices = deviceGroupToDeviceService.selectList(new EntityWrapper<DeviceGroupToDevice>().in("device_group_id", deviceGroups.stream().map(DeviceGroup::getId).collect(Collectors.toList())));
                if (CollectionUtils.isEmpty(deviceGroupToDevices)) {
                    LeaseException.throwSystemException(LeaseExceEnums.DEVICE_CANNOT_ACCESS);
                }
                List<String> snos = deviceGroupToDevices.stream().map(DeviceGroupToDevice::getDeviceSno).collect(Collectors.toList());
                if (CollectionUtils.isEmpty(snos)) {
                    LeaseException.throwSystemException(LeaseExceEnums.DEVICE_CANNOT_ACCESS);
                }
                devices = deviceService.selectBatchIds(snos);
            } else {
                if (CollectionUtils.isNotEmpty(dto.getDeviceSnos())) {
                    devices = deviceService.selectList(new EntityWrapper<Device>().in("sno", dto.getDeviceSnos()).in("owner_id", sysUserService.resolveSysUserAllSubIds(
                            currentUserOwner)));
                    if (devices.size() != dto.getDeviceSnos().size()) {
                        LeaseException.throwSystemException(LeaseExceEnums.DEVICE_CANNOT_ACCESS);
                    }
                }
            }
            if (CollectionUtils.isEmpty(devices)) {
                LeaseException.throwSystemException(LeaseExceEnums.DEVICE_NOT_IN_LAUNCH_AREA);
            }
            dto.setDevices(devices);
        }
    }


    @Override
    public void afterPropertiesSet() throws Exception {


        map.put(AssignDestinationType.AGENT, dto -> {
            Agent agent = agentService.selectOne(new EntityWrapper<Agent>().eq("id", dto.getAssignedId()).in("sys_user_id", sysUserService.resolveSysUserAllSubAdminIds(getCurrentUserOwner(dto))));
            if (Objects.isNull(agent)) {
                LeaseException.throwSystemException(LeaseExceEnums.DEVICE_ASSIGN_AGENT_NOT_EXIST);
            }
            dto.setIsAgent(true);
            dto.setAssignedName(agent.getName());
            return agent.getSysAccountId();
        });

        map.put(AssignDestinationType.LAUNCH_AREA, dto -> {
            DeviceLaunchArea launchArea = deviceLaunchAreaService.getLaunchAreaInfoById(dto.getAssignedId());
            if (Objects.isNull(launchArea)) {
                LeaseException.throwSystemException(LeaseExceEnums.DEVICE_WITHOUT_LAUNCH_AREA);
            }
            dto.setLaunchAreaIds(Collections.singletonList(launchArea.getId()));
            return launchArea.getId();
        });
    }


    @FunctionalInterface
    protected interface AccountResolver {
        Integer resolve(DeviceForAssignDto dto);
    }

    private SysUser getCurrentUserOwner(DeviceForAssignDto dto) {
        return dto.getCurrentUser() == null ? sysUserService.getCurrentUserOwner() :
                sysUserService.getSysUserOwner(dto.getCurrentUser());
    }
}
