package com.gizwits.lease.maintainer.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.gizwits.boot.api.SmsApi;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.dto.SysUserForAddDto;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.SysConfigUtils;
import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.device.entity.DeviceLaunchArea;
import com.gizwits.lease.device.service.DeviceLaunchAreaService;
import com.gizwits.lease.maintainer.dao.MaintainerDao;
import com.gizwits.lease.maintainer.dto.MaintainerAddDto;
import com.gizwits.lease.maintainer.dto.MaintainerInfoDto;
import com.gizwits.lease.maintainer.dto.MaintainerQueryDto;
import com.gizwits.lease.maintainer.service.MaintainerService;
import com.gizwits.lease.model.DeleteDto;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MaintainerServiceImpl extends ServiceImpl<MaintainerDao, SysUser> implements MaintainerService {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private DeviceLaunchAreaService deviceLaunchAreaService;


	@Override
	public void add(MaintainerAddDto dto) {
		CommonSystemConfig commonSystemConfig = SysConfigUtils.get(CommonSystemConfig.class);
		SysUserForAddDto sysUserForAddDto = new SysUserForAddDto();
		sysUserForAddDto.setUsername(dto.getMobile());
		sysUserForAddDto.setPassword(RandomStringUtils.randomAlphanumeric(6));
		sysUserForAddDto.setNickName(dto.getNickname());
		sysUserForAddDto.setMobile(dto.getMobile());
		sysUserForAddDto.setIsEnable(1);
		sysUserForAddDto.setRoleIds(Collections.singletonList(commonSystemConfig.getMaintenanceRoleId()));
		if (sysUserService.add(sysUserForAddDto)>0) {
			String apiKey = commonSystemConfig.getMessageApiKey();
			Map<String, String> params = new HashMap<>();
			params.put("pwd", sysUserForAddDto.getPassword());
			SmsApi.sendSms(apiKey, "【机智云】您的密码是：#pwd#", dto.getMobile(), params);
		}
	}

	@Override
	public DeleteDto del(List<Integer> ids) {
		DeleteDto deleteDto = new DeleteDto();
		List<String> fails = new LinkedList<>();
		int count = 0;
		SysUser forDel = new SysUser();
		forDel.setIsEnable(0);
		sysUserService.update(forDel, new EntityWrapper<SysUser>().in("id", ids));
		//清空投放点的维护人员
		DeviceLaunchArea deviceLaunchArea = new DeviceLaunchArea();
		deviceLaunchArea.setMaintainerId(null);
		deviceLaunchArea.setMaintainerName(null);
		deviceLaunchAreaService.update(deviceLaunchArea,new EntityWrapper<DeviceLaunchArea>().in("maintainer_id",ids).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
		deleteDto.setFails(fails);
		deleteDto.setSucceed(count);
		return deleteDto;
	}

	@Override
	public Page<MaintainerInfoDto> list(Pageable<MaintainerQueryDto> pageable) {
		CommonSystemConfig commonSystemConfig = SysConfigUtils.get(CommonSystemConfig.class);
		pageable.getQuery().setMaintainerRoleId(commonSystemConfig.getMaintenanceRoleId());
		SysUser userOwner = sysUserService.getCurrentUserOwner();
		List<Integer> userIds = sysUserService.resolveSysUserAllSubIds(userOwner);
		pageable.getQuery().setOwnerIds(userIds);
		List<MaintainerInfoDto> list = baseMapper.list(pageable, pageable.getQuery());
		Page<MaintainerInfoDto> result = new Page<>();
		BeanUtils.copyProperties(pageable, result);
		result.setRecords(list);
		return result;
	}

	@Override
	public MaintainerInfoDto detail(Integer id) {
		return baseMapper.detail(id);
	}
}
