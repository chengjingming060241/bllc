package com.gizwits.lease.version.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.lease.version.entity.AppVersion;
import com.baomidou.mybatisplus.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * app版本记录表 服务类
 * </p>
 *
 * @author yinhui
 * @since 2018-01-23
 */
public interface AppVersionService extends IService<AppVersion> {

    Page<AppVersion> list(Pageable pageable);

    boolean add(Integer id, MultipartFile file, String version, String description);

    AppVersion selectById(Integer id);

    void delete(List<Integer> ids);

    AppVersion getNewVersion();
}
