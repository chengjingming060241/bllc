package com.gizwits.boot.sys.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.common.WebCommonConfig;
import com.gizwits.boot.dto.SysPermissionForDetailDto;
import com.gizwits.boot.dto.SysVersionForAddDto;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.dao.SysVersionDao;
import com.gizwits.boot.sys.entity.*;
import com.gizwits.boot.sys.service.SysRoleService;
import com.gizwits.boot.sys.service.SysRoleToVersionService;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.sys.service.SysVersionService;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.SysConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统版本 服务实现类
 * </p>
 * <p>
 * 一个接口对应多个版本，可由前端自定义，但versionCode要对应后端的版本标识
 *
 * @author Joke
 * @since 2018-01-23
 */
@Service
public class SysVersionServiceImpl extends ServiceImpl<SysVersionDao, SysVersion> implements SysVersionService {
	private static final Logger log = LoggerFactory.getLogger(SysVersionServiceImpl.class);

	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleToVersionService sysRoleToVersionService;
	@Autowired
	private SysRoleService sysRoleService;

	@Override
	public void add(Integer pPermissionId, List<SysVersionForAddDto> dtoList) {
		if (!ParamUtil.isNullOrEmptyOrZero(dtoList)) {
			SysUser current = sysUserService.getCurrentUser();
			List<SysVersion> forInsertBatch = new ArrayList<>();
			for (SysVersionForAddDto versionForAddDto : dtoList) {
				SysVersion version = new SysVersion();
				BeanUtils.copyProperties(versionForAddDto, version);
				version.setpPermissionId(pPermissionId);
				version.setSysUserId(current.getId());
				version.setSysUserName(current.getUsername());
				forInsertBatch.add(version);
			}
			insertBatch(forInsertBatch);
		}
	}

	@Override
	public List<SysPermissionForDetailDto> mergePermissionVersion(List<SysPermission> permissionList,
			List<SysVersion> versionList) {
		// 前端要求将版本放到权限列表的同一个list，尽管它实际上属于权限的下一层级。
		List<SysPermissionForDetailDto> result =
				permissionList.stream().map(SysPermissionForDetailDto::new).collect(Collectors.toList());
		List<SysPermissionForDetailDto> versionDtoList =
				versionList.stream().map(SysPermissionForDetailDto::new).collect(Collectors.toList());
		result.addAll(versionDtoList);
		return result;
	}

	@Override
	public List<SysVersion> getVersionsByPermissions(List<SysPermission> permissionList) {
		List<Integer> permissionIds = permissionList.stream().map(SysPermission::getId).collect(Collectors.toList());
		if (permissionIds.isEmpty()) {
			return new LinkedList<>();
		} else {
			return selectList(new EntityWrapper<SysVersion>().in("p_permission_id", permissionIds));
		}
	}

	@Override
	public List<SysVersion> getVersionsByRoleIds(List<Integer> roleIds) {
		if (roleIds.contains(sysRoleService.getSuperManagerRoleId())) {
			return selectList(null);
		}else {
			//根据角色id查找到最上级的角色id（admin除外）
			roleIds = sysRoleService.getSuperRoleIds(roleIds);
			List<Integer> versionIds =
					sysRoleToVersionService.selectList(new EntityWrapper<SysRoleToVersion>().in("role_id", roleIds))
							.stream().map(SysRoleToVersion::getVersionId).collect(Collectors.toList());
			if (versionIds.isEmpty()) {
				return new LinkedList<>();
			} else {
				return selectBatchIds(versionIds);
			}
		}
	}


}
