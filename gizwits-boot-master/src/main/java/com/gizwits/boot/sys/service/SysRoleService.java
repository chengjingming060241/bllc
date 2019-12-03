package com.gizwits.boot.sys.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.boot.common.SysDeleteDto;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.dto.SysRoleForAddDto;
import com.gizwits.boot.dto.SysRoleForDetailDto;
import com.gizwits.boot.dto.SysRoleForListDto;
import com.gizwits.boot.dto.SysRoleForQueryDto;
import com.gizwits.boot.sys.entity.SysRole;

/**
 * <p>
 * 系统角色表 服务类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 分页查询
     *
     * @param pageDto
     */
    Page<SysRoleForListDto> getListByPage(Pageable<SysRoleForQueryDto> pageDto);

    /**
     * 获取当前登录人下的直接角色列表
     */
    List<SysRole> getCreatedRolesBySysUserId(Integer sysUserId);

    List<SysRole> getCreatedRolesBySysUserId(List<Integer> sysUserIds);

    /**
     * 批量删除角色
     * @param roleIds
     * @return
     */
    boolean deleteSysRoles(List<Integer> roleIds);

    /**
     * 获取userId所在的角色
     */
    List<SysRole> getRolesByUserId(Integer userId);


    /**
     * 添加
     */
    boolean add(SysRoleForAddDto dto);


    /**
     * 详情
     */
    SysRoleForDetailDto detail(Integer id);

    /**
     * 更新
     */
    boolean update(SysRoleForAddDto dto);

    String delete(List<Integer> ids);

    /**
     * 超级管理员角色id
     *
     * @return 超级管理员角色id
     */
    Integer getSuperManagerRoleId();

    /**
     * 获得用户创建的维护人员角色
     * @param sysUserId
     * @return
     */
    Integer getRoleIdBySysUserId(Integer sysUserId);

    /***
     * 查询最上级的角色id
     * @param roleIds
     * @return
     */
    List<Integer> getSuperRoleIds(List<Integer> roleIds);

}
