package com.gizwits.boot.sys.dao;

import com.gizwits.boot.sys.entity.SysUser;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 系统用户表 Mapper 接口
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
public interface SysUserDao extends BaseMapper<SysUser> {
	Map<String, String> getSystemNameAndLogo(@Param("parentIds") List<Integer> parentIds);
}