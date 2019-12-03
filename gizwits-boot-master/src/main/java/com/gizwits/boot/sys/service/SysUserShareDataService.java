package com.gizwits.boot.sys.service;

import com.baomidou.mybatisplus.service.IService;
import com.gizwits.boot.dto.SysUserForUpdateDto;
import com.gizwits.boot.dto.SysUserShareDataDto;
import com.gizwits.boot.enums.SysUserShareDataEnum;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.entity.SysUserShareData;

/**
 * <p>
 * 系统用户共享数据表 服务类
 * </p>
 *
 * @author 
 * @since 2018-02-09
 */
public interface SysUserShareDataService extends IService<SysUserShareData> {

    /**
     * 系统用户是否共享数据
     */
    boolean isSysUserShareData(SysUser sysUser, SysUserShareDataEnum shareDataEnum);

    /**
     * 查询系统用户共享数据设置
     */
    SysUserShareDataDto getSysUserShareData(SysUser sysUser);

    /**
     * 设置共享数据
     */
    void updateSysUserShareData(SysUser sysUser, SysUserShareDataDto dto);
}
