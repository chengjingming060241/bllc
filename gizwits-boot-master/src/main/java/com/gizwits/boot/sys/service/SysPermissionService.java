package com.gizwits.boot.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.boot.common.SysDeleteDto;
import com.gizwits.boot.dto.MenuDto;
import com.gizwits.boot.dto.PageDto;
import com.gizwits.boot.dto.SysPermissionForDetailDto;
import com.gizwits.boot.dto.SysPermissionForUpdateDto;
import com.gizwits.boot.sys.entity.SysPermission;

/**
 * <p>
 * 系统权限(菜单)表 服务类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
public interface SysPermissionService extends IService<SysPermission> {

    List<SysPermission> getSysPermissionListByIds(List<Integer> ids);

    /**
     * 分页查询
     *
     * @param pageDto
     * @return
     */
    Page<SysPermissionForDetailDto> getListByPage(PageDto pageDto);

    /**
     * 查询角色权限的左侧菜单
     *
     * @param ids
     * @param Type
     * @return
     */
    List<MenuDto> getSysPermissionListByIdsAndType(List<Integer> ids, Integer Type);

    /**
     * 通过父级权限id查询
     *
     * @param ids            权限id
     * @param p_permissionId 父级id
     * @return
     */
    List<MenuDto> getSysPermissionListByIdsAndPPermissionId(List<Integer> ids, Integer p_permissionId);

    /**
     * 更新
     */
    boolean update(SysPermissionForUpdateDto dto);

    String delete(List<Integer> ids);

    SysPermission selectById(Integer id);
}
