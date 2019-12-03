package com.gizwits.boot.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.gizwits.boot.sys.entity.SysUserToRole;

/**
 * <p>
 * 用户角色关系表(多对多) 服务类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
public interface SysUserToRoleService extends IService<SysUserToRole> {
    /**
     * 根据用户查询角色
     * @param userId
     * @return
     */
    List<SysUserToRole> getSysUserToRoleListByUserId(Integer userId);

    /**
     * 查询权限id
     */
    List<Integer> getPermissionsByUserId(Integer userId);

}
