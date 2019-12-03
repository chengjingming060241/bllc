package com.gizwits.boot.sys.service;

import com.gizwits.boot.sys.entity.SysUserExt;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 系统用户扩展表 服务类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
public interface SysUserExtService extends IService<SysUserExt> {

    /**
     * 更新
     */
    boolean update(SysUserExt ext);

    SysUserExt getSysUserExtByWeixinAppid(String wxAppid);

    SysUserExt getSysUserExtByAlipayAppid(String alipayAppid);

    SysUserExt getLatestSysUserExt();
}
