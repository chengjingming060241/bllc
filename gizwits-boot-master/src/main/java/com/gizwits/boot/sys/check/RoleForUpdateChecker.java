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
 * Checker - 更新角色
 *
 * @author lilh
 * @date 2017/7/27 12:26
 */
@Order(10)
@Component
public class RoleForUpdateChecker extends SysUserForUpdateChecker {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    protected SysExceptionEnum getSysExceptionEnum() {
        return SysExceptionEnum.ROLE_NOT_EXIST;
    }

    @Override
    protected boolean exist(SysUserCheckerDto dto) {
        int expect = dto.getRoleIds().size();
        int real = sysRoleService.selectCount(new EntityWrapper<SysRole>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).in("id", dto.getRoleIds()).eq("sys_user_id", sysUserService.getCurrentUser().getId()));
        return expect != real;
    }
}
