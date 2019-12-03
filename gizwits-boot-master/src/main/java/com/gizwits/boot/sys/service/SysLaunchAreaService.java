package com.gizwits.boot.sys.service;


import com.baomidou.mybatisplus.service.IService;
import com.gizwits.boot.sys.entity.SysLaunchArea;

import java.util.List;

/**
 * <p>
 * 设备投放点表 服务类
 * </p>
 *
 * @author yinhui
 * @since 2017-07-12
 */
public interface SysLaunchAreaService extends IService<SysLaunchArea> {



    List<SysLaunchArea> selectAllSysLaunchAreaById();


}
