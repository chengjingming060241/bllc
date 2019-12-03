package com.gizwits.lease;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserDeleteService;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.lease.device.entity.Device;
import com.gizwits.lease.device.service.DeviceService;
import com.gizwits.lease.manager.entity.Agent;
import com.gizwits.lease.manager.entity.Operator;
import com.gizwits.lease.manager.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Description:
 * User: yinhui
 * Date: 2018-02-05
 */
@Component
public class SysUserDeleteHelper implements SysUserDeleteService {

    @Autowired
    private SysUserService sysUserService;


    @Autowired
    private AgentService agentService;

    @Autowired
    private DeviceService deviceService;

    @Override
    public boolean beforeDelete(Integer id) {
        SysUser current = sysUserService.getCurrentUserOwner();
        List<Integer> userIds = sysUserService.resolveSysUserAllSubAdminIds(current);
        SysUser sysUser = sysUserService.selectById(id);
        if (!ParamUtil.isNullOrEmptyOrZero(sysUser)) {
            if (!userIds.contains(sysUser.getSysUserId())) {
                return false;
            }
            //判断用户是否拥有下级和设备
            int deviceCount = deviceService.selectCount(new EntityWrapper<Device>().eq("owner_id", id).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
            EntityWrapper<SysUser> entityWrapper = new EntityWrapper<>();
            entityWrapper.eq("sys_user_id", id).or().eq("parent_admin_id", id);
            entityWrapper.andNew("is_deleted = 0");
            int sonSysUserNum = sysUserService.selectCount(entityWrapper);
            if (deviceCount <= 0 && sonSysUserNum <= 0) {

                Agent agent = agentService.getAgentBySysAccountId(id);
                if (Objects.nonNull(agent)) {
                    agentService.delete(Collections.singletonList(agent.getId()));
                }
                return true;
            }
        }
        return false;

    }
}
