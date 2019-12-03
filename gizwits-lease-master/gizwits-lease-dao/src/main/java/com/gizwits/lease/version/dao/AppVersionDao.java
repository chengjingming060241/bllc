package com.gizwits.lease.version.dao;

import com.gizwits.lease.version.entity.AppVersion;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
  * app版本记录表 Mapper 接口
 * </p>
 *
 * @author yinhui
 * @since 2018-01-23
 */
public interface AppVersionDao extends BaseMapper<AppVersion> {

     AppVersion getNewVersion();
}