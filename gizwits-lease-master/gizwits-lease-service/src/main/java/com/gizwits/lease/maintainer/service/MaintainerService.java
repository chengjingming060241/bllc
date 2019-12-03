package com.gizwits.lease.maintainer.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.lease.maintainer.dto.MaintainerAddDto;
import com.gizwits.lease.maintainer.dto.MaintainerInfoDto;
import com.gizwits.lease.maintainer.dto.MaintainerQueryDto;
import com.gizwits.lease.model.DeleteDto;

import java.util.List;

public interface MaintainerService extends IService<SysUser> {

	void add(MaintainerAddDto dto);
	DeleteDto del(List<Integer> ids);
	Page<MaintainerInfoDto> list(Pageable<MaintainerQueryDto> pageable);
	MaintainerInfoDto detail(Integer id);

}
