package com.gizwits.lease.version.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.QueryResolverUtils;
import com.gizwits.boot.utils.SysConfigUtils;
import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.version.entity.AppVersion;
import com.gizwits.lease.version.dao.AppVersionDao;
import com.gizwits.lease.version.service.AppVersionService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * <p>
 * app版本记录表 服务实现类
 * </p>
 *
 * @author yinhui
 * @since 2018-01-23
 */
@Service
public class AppVersionServiceImpl extends ServiceImpl<AppVersionDao, AppVersion> implements AppVersionService {

    protected static Logger logger = LoggerFactory.getLogger("MESSAGE_LOGGER");
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private AppVersionDao appVersionDao;

    @Override
    public Page<AppVersion> list(Pageable pageable) {
        Page<AppVersion> page = new Page<>();
        BeanUtils.copyProperties(pageable, page);
        return selectPage(page, QueryResolverUtils.parse(pageable, new EntityWrapper<AppVersion>().orderBy("ctime",false).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode())));
    }

    @Override
    public boolean add(Integer id, MultipartFile file, String version, String description) {
        SysUser current = sysUserService.getCurrentUser();
        String newFilename = "";
        try {
            String finame = file.getOriginalFilename();
            if (!finame.contains(".")) {
                LeaseException.throwSystemException(LeaseExceEnums.FILE_SUFFIX_ERROT);
            }
            String last = finame.substring(finame.lastIndexOf(".")+1);
            if (!last.equals("apk")) {
                LeaseException.throwSystemException(LeaseExceEnums.FILE_SUFFIX_ERROT);
            }
            String suffix = finame.substring(finame.lastIndexOf("."));
            newFilename = UUID.randomUUID() + suffix;
            File newFile = new File(SysConfigUtils.get(CommonSystemConfig.class).getAppVersionPath() + newFilename);
            File dir = newFile.getParentFile();
            if (!dir.exists()) dir.mkdirs();
            file.transferTo(newFile);
        } catch (Exception ex) {
            logger.error("==============>图片上传失败，原因：" + ex);
            ex.printStackTrace();
            LeaseException.throwSystemException(LeaseExceEnums.UPLOAD_ERROR);
        }
        AppVersion appVersion = null;
        if (id != null) {
            appVersion = selectById(id);
        }
        if (ParamUtil.isNullOrEmptyOrZero(appVersion)) {
            appVersion = new AppVersion();
        }
        appVersion.setCtime(new Date());
        appVersion.setUtime(new Date());
        appVersion.setVersion(version);
        appVersion.setUrl(newFilename);
        appVersion.setDescription(description);
        appVersion.setVersion(version);
        appVersion.setSysUserId(current.getId());
        appVersion.setSysUserName(current.getUsername());
        return insertOrUpdate(appVersion);
    }

    @Override
    public AppVersion selectById(Integer id) {
        AppVersion appVersion = selectOne(new EntityWrapper<AppVersion>().eq("id", id).eq("is_deleted", DeleteStatus.NOT_DELETED.getCode()));
        getUrl(appVersion);
        return   appVersion;
    }

    private void getUrl(AppVersion appVersion) {
        if (!ParamUtil.isNullOrEmptyOrZero(appVersion)) {
            String pictureUrl = appVersion.getUrl();
            if (StringUtils.isNotBlank(pictureUrl)) {
                String[] picUrls = pictureUrl.split(";");
                if (picUrls.length > 0) {
                    for (int i = 0; i < picUrls.length; i++) {
                        picUrls[i] = "/appVersion/" + picUrls[i];
                    }
                    appVersion.setUrl(String.join(";", picUrls));
                }
            }
        }
    }

    @Override
    public void delete(List<Integer> ids) {
        AppVersion appVersion = new AppVersion();
        appVersion.setUtime(new Date());
        appVersion.setIsDeleted(DeleteStatus.DELETED.getCode());
        EntityWrapper<AppVersion> entityWrapper = new EntityWrapper<>();
        entityWrapper.in("id",ids);
        update(appVersion,entityWrapper);
    }

    @Override
    public AppVersion getNewVersion(){
        AppVersion appVersion = appVersionDao.getNewVersion();
        getUrl(appVersion);
        return appVersion;
    }

}
