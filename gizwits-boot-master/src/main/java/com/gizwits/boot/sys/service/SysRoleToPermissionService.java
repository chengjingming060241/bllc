package com.gizwits.boot.sys.service;

import com.gizwits.boot.sys.entity.SysRoleToPermission;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 角色权限关系表(多对多) 服务类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
public interface SysRoleToPermissionService extends IService<SysRoleToPermission> {

    List<SysRoleToPermission> getSysRoleToPermissionListByRoles(List<Integer> roleIds);
}
