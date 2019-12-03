package com.gizwits.boot.sys.service.impl;

import java.io.Serializable;
import java.util.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.base.Constants;
import com.gizwits.boot.common.SysDeleteDto;
import com.gizwits.boot.dto.*;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.dao.SysPermissionDao;
import com.gizwits.boot.sys.entity.SysPermission;
import com.gizwits.boot.sys.entity.SysRoleToPermission;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.entity.SysVersion;
import com.gizwits.boot.sys.service.SysPermissionService;
import com.gizwits.boot.sys.service.SysRoleToPermissionService;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.sys.service.SysVersionService;
import com.gizwits.boot.utils.ParamUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统权限(菜单)表 服务实现类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionDao, SysPermission> implements SysPermissionService {

    @Autowired
    private SysVersionService sysVersionService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleToPermissionService sysRoleToPermissionService;

    @Override
    public List<SysPermission> getSysPermissionListByIds(List<Integer> ids) {
        return selectBatchIds(ids);
    }

    @Override
    public Page<SysPermissionForDetailDto> getListByPage(PageDto pageDto) {
        //设置条件
        Page<SysPermission> page = new Page<SysPermission>();
        BeanUtils.copyProperties(pageDto, page);
        EntityWrapper<SysPermission> entityWrapper = new EntityWrapper<SysPermission>();
        page.setCondition(pageDto.getCondition());
        //设置时间条件
        if (!ParamUtil.isNullOrEmptyOrZero(pageDto.getStartTime())) {
            entityWrapper.ge(Constants.CTIME, pageDto.getStartTime());
        }
        if (!ParamUtil.isNullOrEmptyOrZero(pageDto.getEndTime())) {
            entityWrapper.lt(Constants.CTIME, pageDto.getEndTime());
        }

        //排序
        entityWrapper.eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        entityWrapper.orderBy(page.getOrderByField(), page.isAsc());
        Page<SysPermission> selectPage = selectPage(page, entityWrapper.eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        selectPage.setRecords(selectList(entityWrapper));
        Page<SysPermissionForDetailDto> result = new Page<>();
        BeanUtils.copyProperties(selectPage, result);
        List<SysVersion> versionList = sysVersionService.getVersionsByPermissions(selectPage.getRecords());
        result.setRecords(sysVersionService.mergePermissionVersion(selectPage.getRecords(), versionList));

        return result;
    }

    @Override
    public List<MenuDto> getSysPermissionListByIdsAndType(List<Integer> ids, Integer type) {
        EntityWrapper<SysPermission> entityWrapper = new EntityWrapper<>();
        entityWrapper.in("id", ids).eq("permission_type", type).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        entityWrapper.orderBy("sort", true);
        return getMenuDtoList(selectList(entityWrapper));
    }

    @Override
    public List<MenuDto> getSysPermissionListByIdsAndPPermissionId(List<Integer> ids, Integer p_permissionId) {
        EntityWrapper<SysPermission> entityWrapper = new EntityWrapper<>();
        entityWrapper.in("id", ids).eq("p_permission_id", p_permissionId).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode());
        entityWrapper.orderBy("sort", true);
        return getMenuDtoList(selectList(entityWrapper));
    }

    @Override
    public boolean update(SysPermissionForUpdateDto dto) {
        SysPermission exist = selectById(dto.getId());
        if (Objects.isNull(exist)) {
            return false;
        }
        BeanUtils.copyProperties(dto, exist);
        exist.setUtime(new Date());

        if (updateById(exist)) {
            //删除旧的版本
            sysVersionService.delete(new EntityWrapper<SysVersion>().eq("p_permission_id", exist.getId()));
            //添加新的版本
            sysVersionService.add(exist.getId(), dto.getVersions());
        }
        return true;
    }

    /**
     * 将提取权限中的一部分字段
     *
     * @param sysPermissionList
     * @return
     */
    public List<MenuDto> getMenuDtoList(List<SysPermission> sysPermissionList) {
        List<MenuDto> menuDtos = new ArrayList<MenuDto>(sysPermissionList.size());
        if (!ParamUtil.isNullOrEmptyOrZero(sysPermissionList)) {
            for (SysPermission sysPermission : sysPermissionList) {
                MenuDto menuDto = new MenuDto();
                menuDto.setId(sysPermission.getId());
                menuDto.setBpId(sysPermission.getpPermissionId());
                menuDto.setMpId(sysPermission.getpPermissionId());
                menuDto.setName(sysPermission.getPermissionName());
                menuDto.setIcon(sysPermission.getIcon());
                menuDto.setRouter(sysPermission.getPermissionKey());
                menuDtos.add(menuDto);
            }
        }
        return menuDtos;
    }

    @Override
    public String delete(List<Integer> ids) {
        SysUser current = sysUserService.getCurrentUser();
        List<Integer> userIds = sysUserService.resolveOwnerAccessableUserIds(current);
        StringBuilder sb = new StringBuilder();
        List<String> fails = new LinkedList<>();
        if (CollectionUtils.isNotEmpty(ids)) {
            for (Integer id : ids) {
                SysPermission sysPermission = selectById(id);
                if (!ParamUtil.isNullOrEmptyOrZero(sysPermission)) {
                    if (userIds.contains(sysPermission.getSysUserId())) {
                        sysPermission.setUtime(new Date());
                        sysPermission.setIsDeleted(DeleteStatus.DELETED.getCode());
                        updateById(sysPermission);
                        //删除角色与权限的对应关系
                        SysRoleToPermission sysRoleToPermission = new SysRoleToPermission();
                        sysRoleToPermission.setUtime(new Date());
                        sysRoleToPermission.setIsDeleted(DeleteStatus.DELETED.getCode());
                        EntityWrapper<SysRoleToPermission> wrapper = new EntityWrapper<>();
                        wrapper.eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()).eq("permission_id", id);
                        sysRoleToPermissionService.update(sysRoleToPermission, wrapper);
                    } else {
                        fails.add(sysPermission.getPermissionName());
                    }
                }
            }
        }
        switch (fails.size()) {
            case 0:
                sb.append("删除成功");
                break;
            case 1:
                sb.append("您欲删除的权限[" + fails.get(0) + "]删除失败");
                break;
            case 2:
                sb.append("您欲删除的权限[" + fails.get(0) + "],[" + fails.get(1) + "]删除失败");
                break;
            default:
                sb.append("您欲删除的权限[" + fails.get(0) + "],[" + fails.get(1) + "]等删除失败");
                break;
        }
        return sb.toString();
    }


    @Override
    public SysPermission selectById(Integer id) {
        return selectOne(new EntityWrapper<SysPermission>().eq("id", id).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
    }
}
