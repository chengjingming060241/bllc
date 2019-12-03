package com.gizwits.boot.sys.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.common.SysDeleteDto;
import com.gizwits.boot.common.WebCommonConfig;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.dto.SysRoleForAddDto;
import com.gizwits.boot.dto.SysRoleForDetailDto;
import com.gizwits.boot.dto.SysRoleForListDto;
import com.gizwits.boot.dto.SysRoleForQueryDto;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.enums.SysUserType;
import com.gizwits.boot.exceptions.SysExceptionEnum;
import com.gizwits.boot.exceptions.SystemException;
import com.gizwits.boot.sys.dao.SysRoleDao;
import com.gizwits.boot.sys.entity.*;
import com.gizwits.boot.sys.service.*;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.QueryResolverUtils;
import com.gizwits.boot.utils.SysConfigUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 系统角色表 服务实现类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleDao, SysRole> implements SysRoleService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysUserToRoleService sysUserToRoleService;

    @Autowired
    private SysRoleToPermissionService sysRoleToPermissionService;

    @Autowired
    private SysPermissionService sysPermissionService;

    @Autowired
    private SysVersionService sysVersionService;

    @Autowired
    private SysRoleToVersionService sysRoleToVersionService;

    @Override
    public Page<SysRoleForListDto> getListByPage(Pageable<SysRoleForQueryDto> pageDto) {
        //设置条件
        Page<SysRole> page = new Page<SysRole>();
        SysUser currentUserOwner = sysUserService.getCurrentUserOwner();
        BeanUtils.copyProperties(pageDto, page);
        Wrapper<SysRole> wrapper = new EntityWrapper<SysRole>();

        if (currentUserOwner.getIsAdmin()==3){
            wrapper.eq("id", SysUserType.OPERATOR.getCode())
                    .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        }else {
            wrapper.in("sys_user_id", sysUserService.resolveOwnerAccessableUserIds(currentUserOwner))
                    .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        }

        //按更新时间降序排列
        //page.setOrderByField("utime");
        //page.setAsc(false);
        Page<SysRole> roleList = selectPage(page, QueryResolverUtils.parse(pageDto.getQuery(), wrapper));
        Page<SysRoleForListDto> result = new Page<>();
        BeanUtils.copyProperties(roleList, result);
        result.setRecords(new LinkedList<>());
        roleList.getRecords().forEach(role -> {
            SysRoleForListDto dto = new SysRoleForListDto(role);
            dto.setSysUserCount(getSysUserCountByRoleId(role.getId()));
            result.getRecords().add(dto);

        });
        return result;
    }

    @Override
    public List<SysRole> getCreatedRolesBySysUserId(Integer sysUserId) {
        return selectList(new EntityWrapper<SysRole>().eq("sys_user_id", sysUserId).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }

    @Override
    public List<SysRole> getCreatedRolesBySysUserId(List<Integer> sysUserIds) {
        return selectList(new EntityWrapper<SysRole>().in("sys_user_id", sysUserIds));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteSysRoles(List<Integer> roleIds) {
        EntityWrapper<SysUserToRole> entityWrapper = new EntityWrapper<>();
        entityWrapper.in("role_id", roleIds);
        if (CollectionUtils.isNotEmpty(sysUserToRoleService.selectList(entityWrapper))) {
            throw new SystemException(SysExceptionEnum.SYS_ROLE_ASSOCIATE_USER.getCode(), SysExceptionEnum.SYS_ROLE_ASSOCIATE_USER.getMessage());
        }
        sysUserToRoleService.delete(entityWrapper);
        deleteBatchIds(roleIds);
        return true;
    }

    @Override
    public List<SysRole> getRolesByUserId(Integer userId) {
        List<Integer> roleIds = sysUserToRoleService.selectList(new EntityWrapper<SysUserToRole>().eq("user_id", userId).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()))
                .stream().map(SysUserToRole::getRoleId).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(roleIds)) {
            return selectBatchIds(roleIds);
        }
        return Collections.emptyList();
    }

    @Override
    public boolean add(SysRoleForAddDto dto) {
        SysUser current = sysUserService.getCurrentUser();
        check(current.getId(), dto.getRoleName());
        SysRole role = addRole(dto, current);
        return addRolePermission(dto.getPermissions(), role.getId(), current)
                && addRoleVersion(dto.getVersions(), role.getId(), current);
    }

    @Override
    public SysRoleForDetailDto detail(Integer id) {
        SysRole role = findRoleWithCreator(id);
        if (Objects.isNull(role)) {
            return null;
        }
        SysRoleForDetailDto result = new SysRoleForDetailDto(role);
        result.setSysUserCount(getSysUserCountByRoleId(id));
        if (Objects.equals(id, getSuperManagerRoleId())) {
            List<SysPermission> permissionList = sysPermissionService.selectList(null);
            List<SysVersion> versionList = sysVersionService.getVersionsByPermissions(permissionList);
            result.setPermissions(sysVersionService.mergePermissionVersion(permissionList, versionList));
        } else {
            List<SysRoleToPermission> sysRoleToPermissions = sysRoleToPermissionService.getSysRoleToPermissionListByRoles(Arrays.asList(id));
            if (CollectionUtils.isNotEmpty(sysRoleToPermissions)) {
                Set<Integer> permissionIds = sysRoleToPermissions.stream().map(SysRoleToPermission::getPermissionId).collect(Collectors.toSet());
                List<SysPermission> permissionList = sysPermissionService.selectBatchIds(new ArrayList<>(permissionIds));
                List<SysVersion> versionList = sysVersionService.getVersionsByRoleIds(Arrays.asList(id));
                result.setPermissions(sysVersionService.mergePermissionVersion(permissionList, versionList));
            }
        }
        return result;
    }

    @Override
    public boolean update(SysRoleForAddDto dto) {
        SysRole exist = findRoleWithCreator(dto.getId());
        if (Objects.isNull(exist)) {
            throw new SystemException(SysExceptionEnum.INTERNAL_ERROR.getCode(), SysExceptionEnum.INTERNAL_ERROR.getMessage());
        }

        //修改了角色名称，则同一账号下不能重复
        if (!StringUtils.equalsIgnoreCase(exist.getRoleName(), dto.getRoleName())) {
            check(exist.getSysUserId(), dto.getRoleName());
        }
        BeanUtils.copyProperties(dto, exist);
        exist.setUtime(new Date());
        updateById(exist);
        updateRolePermissions(dto);
        updateRoleVersions(dto);
        return true;
    }

    @Override
    public String delete(List<Integer> ids) {
        SysUser current = sysUserService.getCurrentUserOwner();
        List<Integer> userIds = sysUserService.resolveSysUserAllSubIds(current);
        StringBuilder sb = new StringBuilder();
        List<String> fails = new LinkedList<>();
        for (Integer id : ids) {
            SysRole sysRole = selectById(id);
            if (!ParamUtil.isNullOrEmptyOrZero(sysRole)) {
                if (userIds.contains(sysRole.getSysUserId())) {
                    if (sysUserToRoleService.selectCount(new EntityWrapper<SysUserToRole>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("role_id", id)) <= 0) {
                        sysRole.setUtime(new Date());
                        sysRole.setIsDeleted(DeleteStatus.DELETED.getCode());
                        updateById(sysRole);
                        //删除角色与版本的对应关系
                        SysRoleToVersion sysRoleToVersion = new SysRoleToVersion();
                        sysRoleToVersion.setUtime(new Date());
                        sysRoleToVersion.setIsDeleted(DeleteStatus.DELETED.getCode());
                        sysRoleToVersionService.update(sysRoleToVersion, new EntityWrapper<SysRoleToVersion>().eq("role_id", id));
                        //删除角色与权限的对应关系
                        SysRoleToPermission sysRoleToPermission = new SysRoleToPermission();
                        sysRoleToPermission.setUtime(new Date());
                        sysRoleToPermission.setIsDeleted(DeleteStatus.DELETED.getCode());
                        sysRoleToPermissionService.update(sysRoleToPermission, new EntityWrapper<SysRoleToPermission>().eq("role_id", id));

                    } else {
                        fails.add(sysRole.getRoleName());
                    }
                } else {
                    fails.add(sysRole.getRoleName());
                }
            }
        }
        switch (fails.size()) {
            case 0:
                sb.append("删除成功");
                break;
            case 1:
                sb.append("您欲删除的角色[" + fails.get(0) + "]已有账号在使用，请先转移对应账号的角色");
                break;
            case 2:
                sb.append("您欲删除的角色[" + fails.get(0) + "],[" + fails.get(1) + "]已有账号在使用，请先转移对应账号的角色");
                break;
            default:
                sb.append("您欲删除的角色[" + fails.get(0) + "],[" + fails.get(1) + "]等已有账号在使用，请先转移对应账号的角色");
                break;
        }
        return sb.toString();
    }

    public SysRole selectById(Integer id) {
        return selectOne(new EntityWrapper<SysRole>().eq("id", id).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }

    @Override
    public Integer getSuperManagerRoleId() {
        return SysConfigUtils.get(WebCommonConfig.class).getManagerRoleId();
    }

    private void check(Integer userId, String roleName) {
        SysRole exist = selectOne(new EntityWrapper<SysRole>().eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("sys_user_id", userId).eq("role_name", roleName));
        if (Objects.nonNull(exist)) {
            throw new SystemException(SysExceptionEnum.DUP_ROLE_NAME.getCode(), SysExceptionEnum.DUP_ROLE_NAME.getMessage());
        }
    }

    private void updateRolePermissions(SysRoleForAddDto dto) {
        List<Integer> fromFront = Objects.isNull(dto.getPermissions()) ? Collections.emptyList() : dto.getPermissions();
        List<Integer> fromDb = sysRoleToPermissionService.getSysRoleToPermissionListByRoles(Arrays.asList(dto.getId())).stream()
                .map(SysRoleToPermission::getPermissionId).collect(Collectors.toList());
        List<Integer> toDelIds = fromDb.stream().filter(item -> !fromFront.contains(item)).collect(Collectors.toList());
        List<Integer> toAddIds = fromFront.stream().filter(item -> !fromDb.contains(item)).collect(Collectors.toList());

        //删除
        if (CollectionUtils.isNotEmpty(toDelIds)) {
            sysRoleToPermissionService.delete(new EntityWrapper<SysRoleToPermission>().eq("role_id", dto.getId()).in("permission_id", toDelIds));
        }
        //添加
        if (CollectionUtils.isNotEmpty(toAddIds)) {
            addRolePermission(toAddIds, dto.getId(), sysUserService.getCurrentUser());
        }
    }

    private void updateRoleVersions(SysRoleForAddDto dto) {
        List<Integer> fromFront = Objects.isNull(dto.getVersions()) ? Collections.emptyList() : dto.getVersions();
        List<Integer> fromDb = sysRoleToVersionService
                .selectList(new EntityWrapper<SysRoleToVersion>().in("role_id", Arrays.asList(dto.getId())))
                .stream().map(SysRoleToVersion::getVersionId).collect(Collectors.toList());
        List<Integer> toDelIds = fromDb.stream().filter(item -> !fromFront.contains(item)).collect(Collectors.toList());
        List<Integer> toAddIds = fromFront.stream().filter(item -> !fromDb.contains(item)).collect(Collectors.toList());

        //删除
        if (CollectionUtils.isNotEmpty(toDelIds)) {
            sysRoleToVersionService.delete(new EntityWrapper<SysRoleToVersion>().eq("role_id", dto.getId()).in("version_id", toDelIds));
        }
        //添加
        if (CollectionUtils.isNotEmpty(toAddIds)) {
            addRoleVersion(toAddIds, dto.getId(), sysUserService.getCurrentUser());
        }
    }

    private SysRole findRoleWithCreator(Integer id) {
        Wrapper<SysRole> wrapper = new EntityWrapper<>();
        //只能查找当前登录人创建的角色
        wrapper.eq("id", id).in("sys_user_id", sysUserService.resolveOwnerAccessableUserIds(sysUserService.getCurrentUserOwner()))
                .eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        return selectOne(wrapper);
    }

    private boolean addRolePermission(List<Integer> permissionIds, Integer roleId, SysUser current) {
        if (CollectionUtils.isNotEmpty(permissionIds)) {
            List<SysRoleToPermission> permissions = new ArrayList<>(permissionIds.size());
            permissionIds.forEach(permissionId -> {
                SysRoleToPermission tmp = new SysRoleToPermission();
                tmp.setCtime(new Date());
                tmp.setUtime(tmp.getCtime());
                tmp.setPermissionId(permissionId);
                tmp.setRoleId(roleId);
                tmp.setSysUserId(current.getId());
                tmp.setSysUserName(current.getUsername());
                permissions.add(tmp);
            });
            sysRoleToPermissionService.insertBatch(permissions);
        }
        return true;
    }

    private boolean addRoleVersion(List<Integer> versionIds, Integer roleId, SysUser current) {
        if (CollectionUtils.isNotEmpty(versionIds)) {
            List<SysRoleToVersion> versions = new ArrayList<>(versionIds.size());
            versionIds.forEach(versionId -> {
                SysRoleToVersion tmp = new SysRoleToVersion();
                tmp.setVersionId(versionId);
                tmp.setRoleId(roleId);
                tmp.setSysUserId(current.getId());
                tmp.setSysUserName(current.getUsername());
                tmp.setIsDeleted(DeleteStatus.NOT_DELETED.getCode());
                versions.add(tmp);
            });
            sysRoleToVersionService.insertBatch(versions);
        }
        return true;
    }

    private SysRole addRole(SysRoleForAddDto dto, SysUser current) {
        SysRole role = new SysRole();
        BeanUtils.copyProperties(dto, role);
        role.setId(null);
        role.setCtime(new Date());
        role.setUtime(role.getCtime());
        role.setSysUserId(current.getId());
        role.setSysUserName(current.getUsername());
        role.setIsDeleted(DeleteStatus.NOT_DELETED.getCode());
        insert(role);
        return role;
    }

    private Integer getSysUserCountByRoleId(Integer roleId) {
        return sysUserToRoleService.selectCount(new EntityWrapper<SysUserToRole>().eq("role_id", roleId).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }

    @Override
    public Integer getRoleIdBySysUserId(Integer sysUserId) {
        SysRole sysRole = selectOne(new EntityWrapper<SysRole>().eq("sys_user_id", sysUserId).like("role_name", "维护").eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        if (Objects.nonNull(sysRole)) {
            return sysRole.getId();
        }
        return null;
    }

    @Override
    public List<Integer> getSuperRoleIds(List<Integer> roleIds) {
        List<Integer> roleList = new LinkedList<>();
        //获取admin的用户id
        Integer adminUserId = SysConfigUtils.get(WebCommonConfig.class).getManagerUserId();
        SysRole sysRole = selectById(roleIds.get(0));
        if (sysRole.getSysUserId().equals(adminUserId)){
            //admin直接创建的角色id，直接返回不做处理
            return roleIds;
        }
        SysUser parent = sysUserService.selectById(sysRole.getSysUserId());
        while (!parent.getParentAdminId().equals(adminUserId)) {
            parent = sysUserService.selectById(parent.getParentAdminId());
        }
        if (!ParamUtil.isNullOrEmptyOrZero(parent)) {
            roleList = sysUserToRoleService.getSysUserToRoleListByUserId(parent.getId()).stream().map(SysUserToRole::getRoleId).collect(Collectors.toList());
        }
        return roleList == null ? roleIds : roleList;
    }
}
