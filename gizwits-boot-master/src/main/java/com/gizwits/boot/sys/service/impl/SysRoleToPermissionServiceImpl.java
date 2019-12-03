package com.gizwits.boot.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.entity.SysRoleToPermission;
import com.gizwits.boot.sys.dao.SysRoleToPermissionDao;
import com.gizwits.boot.sys.service.SysRoleToPermissionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色权限关系表(多对多) 服务实现类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@Service
public class SysRoleToPermissionServiceImpl extends ServiceImpl<SysRoleToPermissionDao, SysRoleToPermission> implements SysRoleToPermissionService {

    @Override
    public List<SysRoleToPermission> getSysRoleToPermissionListByRoles(List<Integer> roleIds) {
        EntityWrapper<SysRoleToPermission> wrapper = new EntityWrapper<SysRoleToPermission>();
        wrapper.in("role_id",roleIds).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        return  selectList(wrapper);
    }
}
