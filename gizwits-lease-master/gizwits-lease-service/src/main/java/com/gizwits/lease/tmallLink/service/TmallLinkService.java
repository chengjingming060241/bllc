package com.gizwits.lease.tmallLink.service;


import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.lease.tmallLink.dto.*;
import com.gizwits.lease.tmallLink.entity.TmallLink;
import java.util.List;


/**
 * 天猫链接管理服务类
 */
public interface TmallLinkService extends IService<TmallLink> {

    /**
     * 添加
     */
    boolean add(TmallLinkForAddDto dto);


    /**
     * 详情
     */
    TmallLinkForDetailDto detail(Integer id);

    /**
     * 列表
     */
    Page<TmallLinkForListDto> page(Pageable<TmallLinkQueryDto> pageable);

    /**
     * 更新
     */
    TmallLinkForDetailDto update(TmallLinkForUpdateDto dto);


    /**
     * 逻辑删除
     */
    String delete(List<Integer> ids);


}
