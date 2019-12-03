package com.gizwits.boot.sys.check;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.exceptions.SysExceptionEnum;
import com.gizwits.boot.sys.entity.SysRole;
import com.gizwits.boot.sys.service.SysRoleService;
import com.gizwits.boot.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * Checker - 校验角色的合法性
 *
 * @author lilh
 * @date 2017/7/27 11:08
 */
@Order(15)
@Component
public class RoleChecker extends SysUserForAddChecker {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    protected boolean exist(SysUserCheckerDto dto) {
        int expect = dto.getRoleIds().size();
        int real = sysRoleService.selectCount(new EntityWrapper<SysRole>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).in("id", dto.getRoleIds()).in("sys_user_id", sysUserService.resolveOwnerAccessableUserIds(sysUserService.getCurrentUserOwner())));
        return expect != real;
    }

    @Override
    protected SysExceptionEnum getSysExceptionEnum() {
        return SysExceptionEnum.ROLE_NOT_EXIST;
    }
}
