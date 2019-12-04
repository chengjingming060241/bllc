package com.gizwits.lease.device.service.impl;

import java.util.*;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.enums.SysUserType;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.lease.common.perm.CommonRoleResolver;
import com.gizwits.lease.common.perm.DefaultCommonRoleResolverManager;
import com.gizwits.lease.common.perm.SysUserRoleTypeHelper;
import com.gizwits.lease.common.perm.SysUserRoleTypeHelperResolver;
import com.gizwits.lease.common.perm.dto.AssignDestinationDto;
import com.gizwits.lease.constant.DeviceNormalStatus;
import com.gizwits.lease.constant.DeviceSweepCodeStatus;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.entity.DeviceStock;
import com.gizwits.lease.device.entity.dto.DeviceAddDetailsDto;
import com.gizwits.lease.device.entity.dto.DeviceForAssignDto;
import com.gizwits.lease.device.entity.dto.DeviceForUnbindDto;
import com.gizwits.lease.device.service.DeviceAssignService;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.device.service.DeviceStockService;
import com.gizwits.lease.enums.AssignDestinationType;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.manager.entity.Agent;
import com.gizwits.lease.manager.entity.Operator;
import com.gizwits.lease.manager.service.AgentService;
import com.google.gson.JsonArray;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

/**
 * Service - 设备分配
 *
 * @author lilh
 * @date 2017/8/24 18:37
 */
@Slf4j
@Service
public class DeviceAssignServiceImpl implements DeviceAssignService {

    @Autowired
    private SysUserRoleTypeHelperResolver resolverHelper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private DefaultCommonRoleResolverManager resolverManager;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceStockService deviceStockService;

    @Autowired
    private AgentService agentService;



    @Override
    public List<AssignDestinationDto> preAssign() {
        SysUserRoleTypeHelper helper = resolverHelper.resolve(sysUserService.getCurrentUser());
        CommonRoleResolver resolver = resolverManager.getCommonRoleResolver(helper.getCommonRole());
        if (Objects.nonNull(resolver)) {
            return resolver.resolveAssignDest();
        }
        return Collections.emptyList();
    }

    @Override
    public boolean assign(DeviceForAssignDto dto) {
        SysUserRoleTypeHelper helper = resolverHelper.resolve(sysUserService.getCurrentUser());
        CommonRoleResolver resolver = resolverManager.getCommonRoleResolver(helper.getCommonRole());
        if (Objects.nonNull(resolver)) {
            return resolver.assign(dto, helper);
        }
        return false;
    }

    @Override
    public List<String> outOfStock(DeviceForAssignDto dto) {
        if (Objects.isNull(dto.getDeviceAddDetailsDtos())){
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_DONT_EXISTS);
        }
        if (Objects.isNull(agentService.selectById(dto.getAssignedId()))){
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_ASSIGN_AGENT_NOT_EXIST);
        }
        List<String> exist = isExist(dto.getDeviceAddDetailsDtos());
        if (exist.size()>0){
            return exist;
        }
        Date now = new Date();
        for (DeviceAddDetailsDto detailsDto : dto.getDeviceAddDetailsDtos()) {
            DeviceStock sn2 = deviceStockService.selectOne(new EntityWrapper<DeviceStock>()
                    .eq("sn2", detailsDto.getsN2())
                    .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
            sn2.setAgentId(dto.getAssignedId());
            sn2.setOutBatch(dto.getOutBatch());
            sn2.setShiftOutTime(now);
            sn2.setCtime(now);
            sn2.setOutOfStockId(dto.getCurrentUser().getId());
            sn2.setOutOfStockName(dto.getCurrentUser().getRealName());
            sn2.setSweepCodeStatus(DeviceSweepCodeStatus.Out_of_stock.getCode());
            sn2.setIsDeletedOut(DeleteStatus.NOT_DELETED.getCode());
            deviceStockService.insertOrUpdate(sn2);
        }
        return new ArrayList<>();
    }

    /**检查SN2是否存在*/
    private List<String> isExist(List<DeviceAddDetailsDto> detailsDto){
       List<String> strings = new ArrayList<>();
        for (DeviceAddDetailsDto dto :detailsDto){
            if (dto.getsN2()!=null && deviceStockService.selectCount(
                    new EntityWrapper<DeviceStock>().eq("sn2",dto.getsN2()).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode())) == 0){
                strings.add(dto.getsN2());
            }
        }
        return strings;
    }

    @Override
    public boolean assign(String qrcode, Integer finalUserId) {
        Device device = deviceService.getDeviceByQrcode(qrcode);
        if (device == null || device.getStatus().equals(DeviceNormalStatus.WAIT_TO_ENTRY.getCode())) {
            LeaseException.throwSystemException(LeaseExceEnums.QECODE_NOT_EXIST);
        }
        log.info("逐层分配开始，将设备{}当前归属{}，最终分配到管理员{}...", device.getSno(), device.getOwnerId(), finalUserId);
        SysUser finalUser = sysUserService.selectById(finalUserId);
        String deviceOwnerPath = "," + device.getOwnerId() + ",";
        if (finalUser.getTreePath().contains(deviceOwnerPath)) {
            // 设备归属人在被分配目标人的treePath里，可以执行逐层分配
            // 找出设备归属人和被分配目标人之间树状关系中空缺的所有sysUserId，然后逐层分配
            int index = finalUser.getTreePath().indexOf(deviceOwnerPath);
            String assignPath = finalUser.getTreePath().substring(index + deviceOwnerPath.length());
            List<Integer> assignUserIds = Arrays.stream(assignPath.split(","))
                    .filter(StringUtils::isNotBlank).map(Integer::valueOf).collect(Collectors.toList());
            assignUserIds.add(finalUser.getId());
            SysUser fromUser = sysUserService.selectById(device.getOwnerId());
            SysUser toUser = null;
            for (Integer toUserId : assignUserIds) {
                log.info("开始将设备{}从管理员{}分配到管理员{}...", device.getSno(), fromUser.getId(), toUserId);
                toUser = sysUserService.selectById(toUserId);
                DeviceForAssignDto dto = new DeviceForAssignDto();
                dto.setDeviceSnos(Collections.singletonList(device.getSno()));
                if(toUser.getIsAdmin().equals(SysUserType.AGENT.getCode())) {
                    Agent agent = agentService.getAgentBySysAccountId(toUser.getId());
                    dto.setAssignDestinationType(AssignDestinationType.AGENT.getCode());
                    dto.setAssignedId(agent.getId());
                    dto.setIsAgent(true);
                    dto.setAssignedName(agent.getName());
                }
                else {
                    LeaseException.throwSystemException(LeaseExceEnums.DEVICE_CANNOT_ASSIGN);
                }
                dto.setForceAssign(false);
                dto.setResolvedAccountId(toUser.getId());
                dto.setCurrentUser(fromUser);
                SysUserRoleTypeHelper helper = resolverHelper.resolve(fromUser);
                CommonRoleResolver resolver = resolverManager.getCommonRoleResolver(helper.getCommonRole());
                if(resolver.assign(dto, helper)) {
                    log.info("设备{}成功分配到管理员{}。", device.getSno(), toUser.getId());
                } else {
                    log.info("设备{}分配失败！！！分配目标：管理员{}！！！", device.getSno(), toUser.getId());
                    LeaseException.throwSystemException(LeaseExceEnums.DEVICE_CANNOT_ASSIGN);
                }
                fromUser = toUser;
            }
        } else {
            // 设备归属人不在在被分配目标人的treePath里，提示错误
            log.info("设备{}的归属人{}不在在被分配目标{}的treePath:{}里", device.getSno(), device.getOwnerId(),
                    finalUser.getId(), finalUser.getTreePath());
            LeaseException.throwSystemException(LeaseExceEnums.DEVICE_CANNOT_ASSIGN,"设备："+device.getSno()+"已有归属人"+device.getOwnerName());
        }
        log.info("逐层分配完成，设备{}成功分配到管理员{}。", device.getSno(), finalUser.getId());
        return true;
    }

    @Override
    public boolean unbind(DeviceForUnbindDto dto) {
        SysUserRoleTypeHelper helper = resolverHelper.resolve(sysUserService.getCurrentUser());
        CommonRoleResolver resolver = resolverManager.getCommonRoleResolver(helper.getCommonRole());
        if (Objects.nonNull(resolver)) {
            return resolver.unbind(dto, helper);
        }
        return false;
    }
}
