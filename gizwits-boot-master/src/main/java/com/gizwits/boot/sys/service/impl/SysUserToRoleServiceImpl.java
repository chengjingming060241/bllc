package com.gizwits.boot.sys.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.dao.SysUserToRoleDao;
import com.gizwits.boot.sys.entity.SysPermission;
import com.gizwits.boot.sys.entity.SysRoleToPermission;
import com.gizwits.boot.sys.entity.SysUserToRole;
import com.gizwits.boot.sys.service.SysPermissionService;
import com.gizwits.boot.sys.service.SysRoleService;
import com.gizwits.boot.sys.service.SysRoleToPermissionService;
import com.gizwits.boot.sys.service.SysUserToRoleService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色关系表(多对多) 服务实现类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@Service
public class SysUserToRoleServiceImpl extends ServiceImpl<SysUserToRoleDao, SysUserToRole> implements SysUserToRoleService {

    @Autowired
    private SysRoleToPermissionService sysRoleToPermissionService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    public List<SysUserToRole> getSysUserToRoleListByUserId(Integer userId) {
        return selectList(new EntityWrapper<SysUserToRole>().eq("user_id", userId).eq("is_deleted",DeleteStatus.NOT_DELETED.getCode()));
    }

    @Override
    public List<Integer> getPermissionsByUserId(Integer userId) {
        Set<Integer> permissionIds = new HashSet<>();
        List<SysUserToRole> sysUserToRoles = getSysUserToRoleListByUserId(userId);
        if (CollectionUtils.isNotEmpty(sysUserToRoles)) {
            List<Integer> roleIds = sysUserToRoles.stream().map(SysUserToRole::getRoleId).collect(Collectors.toList());
            if (CollectionUtils.isNotEmpty(roleIds)) {
                if (isSuperManager(roleIds)) {
                    //超级管理员，则查询所有权限
                    permissionIds = sysPermissionService.selectList(new EntityWrapper<SysPermission>().eq("is_deleted",0)).stream().map(SysPermission::getId).collect(Collectors.toSet());
                } else {
                    //查询角色对应权限
                    List<SysRoleToPermission> sysRoleToPermissions = sysRoleToPermissionService.getSysRoleToPermissionListByRoles(roleIds);
                    if (CollectionUtils.isNotEmpty(sysRoleToPermissions)) {
                        permissionIds.addAll(sysRoleToPermissions.stream().map(SysRoleToPermission::getPermissionId).collect(Collectors.toList()));
                    }
                }
            }
        }

        return new ArrayList<>(permissionIds);
    }

    private boolean isSuperManager(List<Integer> roleIds) {
        return roleIds.contains(sysRoleService.getSuperManagerRoleId());
    }

}
