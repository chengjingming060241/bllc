package com.gizwits.lease.maintainer.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.lease.maintainer.dto.MaintainerInfoDto;
import com.gizwits.lease.maintainer.dto.MaintainerQueryDto;
import com.gizwits.lease.manager.entity.Agent;

import java.util.List;

/**
 * <p>
  * 维护人员 Mapper 接口
 * </p>
 *
 * @author Joke
 */
public interface MaintainerDao extends BaseMapper<SysUser> {

	List<MaintainerInfoDto> list(Pageable pageable, MaintainerQueryDto query);

	MaintainerInfoDto detail(Integer id);
}