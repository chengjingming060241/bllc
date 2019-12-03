package com.gizwits.boot.sys.service;

import com.gizwits.boot.dto.SysPermissionForDetailDto;
import com.gizwits.boot.dto.SysVersionForAddDto;
import com.gizwits.boot.sys.entity.SysPermission;
import com.gizwits.boot.sys.entity.SysVersion;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 * 系统版本 服务类
 * </p>
 *
 * @author Joke
 * @since 2018-01-23
 */
public interface SysVersionService extends IService<SysVersion> {

	void add(Integer pPermissionId, List<SysVersionForAddDto> dtoList);

	List<SysPermissionForDetailDto> mergePermissionVersion(List<SysPermission> permissionList, List<SysVersion> versionList);

	List<SysVersion> getVersionsByPermissions(List<SysPermission> permissionList);

	List<SysVersion> getVersionsByRoleIds(List<Integer> roleIds);

}
