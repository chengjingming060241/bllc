package com.gizwits.boot.sys.dao;

import com.gizwits.boot.sys.entity.SysUserShareData;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

/**
 * <p>
  * 系统用户共享数据表 Mapper 接口
 * </p>
 *
 * @author 
 * @since 2018-02-09
 */
public interface SysUserShareDataDao extends BaseMapper<SysUserShareData> {

    int shareData(@Param("sysUserId") Integer sysUserId, @Param("shareData") String shareData, @Param("ctime") Date ctime);

    int unshareData(@Param("sysUserId") Integer sysUserId, @Param("shareData") String shareData);
}