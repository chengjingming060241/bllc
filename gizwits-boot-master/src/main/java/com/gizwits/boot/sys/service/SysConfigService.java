package com.gizwits.boot.sys.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.boot.dto.PageDto;
import com.gizwits.boot.dto.SysConfigForUpdateDto;
import com.gizwits.boot.sys.entity.SysConfig;

/**
 * <p>
 * 系统配置表,用来动态添加常量配置 服务类
 * </p>
 *
 * @author rongmc
 * @since 2017-06-20
 */
public interface SysConfigService extends IService<SysConfig> {

    /**
     * 分页查询
     */
    Page<SysConfig> getListByPage(PageDto pageDto);

    /**
     * 更新
     */
    boolean update(SysConfigForUpdateDto dto);
}
