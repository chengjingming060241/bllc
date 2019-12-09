package com.gizwits.lease.message.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gizwits.boot.dto.Pageable;
import com.gizwits.boot.enums.DeleteStatus;
import com.gizwits.boot.exceptions.SysExceptionEnum;
import com.gizwits.boot.exceptions.SystemException;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.PictureUtils;
import com.gizwits.boot.utils.QueryResolverUtils;
import com.gizwits.boot.utils.SysConfigUtils;
import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.manager.entity.Manufacturer;
import com.gizwits.lease.manager.service.ManufacturerService;
import com.gizwits.lease.message.entity.AdvertisementDisplay;
import com.gizwits.lease.message.dao.AdvertisementDisplayDao;
import com.gizwits.lease.message.entity.dto.AdvertisementQueryDto;
import com.gizwits.lease.message.service.AdvertisementDisplayService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>
 * 广告展示表 服务实现类
 * </p>
 *
 * @author yinhui
 * @since 2018-01-19
 */
@Service
public class AdvertisementDisplayServiceImpl extends ServiceImpl<AdvertisementDisplayDao, AdvertisementDisplay> implements AdvertisementDisplayService {
    private static final Logger LOGGER = LoggerFactory.getLogger("MESSAGE_LOGGER");

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private AdvertisementDisplayDao advertisementDisplayDao;


    @Override
    public boolean addOrUpdate(MultipartFile file, Integer showTime, String url, String name, Integer sort,Integer id,Integer type) {
        SysUser user = sysUserService.getCurrentUser();
        AdvertisementDisplay display = null;
        if (!ParamUtil.isNullOrEmptyOrZero(id)) {
           display = selectOne(new EntityWrapper<AdvertisementDisplay>().eq("id", id).eq("sys_user_id", user.getId()).eq("is_deleted", 0));
            if (ParamUtil.isNullOrEmptyOrZero(display)) {
                display = new AdvertisementDisplay();
                display.setCtime(new Date());
            }
        }else {
            display = new AdvertisementDisplay();
            display.setCtime(new Date());
        }
//        //限制排序字段
//        Pattern pattern = Pattern.compile("^[0-9]+$");
//        Matcher matcher = pattern.matcher(sort+"");
//        Matcher matcher1 = pattern.matcher(showTime+"");
//        if (!matcher.matches()|| !matcher1.matches()|| sort<=0 || showTime<=0){
//            LeaseException.throwSystemException(LeaseExceEnums.PARAM_ILLAGEL);
//        }

        if(file != null) {
            String filename = file.getOriginalFilename();
            //判断是否为限制文件类型
            if (!PictureUtils.checkFile(filename)) {
                //限制文件类型，
                throw new SystemException(SysExceptionEnum.PICTURE_SUFFIX_ERROR.getCode(), SysExceptionEnum.PICTURE_SUFFIX_ERROR.getMessage());
            }
            //限制文件大小
            long size = file.getSize();
            if (!PictureUtils.checkSize(size)) {
                throw new SystemException(SysExceptionEnum.PICTURE_OUT_OF_SIZE.getCode(), SysExceptionEnum.PICTURE_OUT_OF_SIZE.getMessage());
            }

            String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String newFilename = UUID.randomUUID() + suffix;

            try {
                String path = SysConfigUtils.get(CommonSystemConfig.class).getFeedBackImagePath();
                File f = new File(path + newFilename);
                File parentFile = f.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                file.transferTo(f);
            } catch (IOException e) {
                LOGGER.error("图片文件上传失败，原因：" + e);
                e.printStackTrace();
                LeaseException.throwSystemException(LeaseExceEnums.UPLOAD_ERROR);

            }
            display.setPicture(newFilename);
        }

        display.setUtime(new Date());
        display.setSysUserId(user.getId());
        display.setSysUserName(user.getUsername());
        display.setShowTime(showTime);
        display.setUrl(url);
        display.setName(name);
        display.setType(type);
        display.setSort(sort);
        return insertOrUpdate(display);
    }

    @Override
    public AdvertisementDisplay detail(Integer id) {
        AdvertisementDisplay display = selectOne(new EntityWrapper<AdvertisementDisplay>().eq("id", id).eq("is_deleted", 0));
        if (!ParamUtil.isNullOrEmptyOrZero(display)) {
            String pictureUrl = display.getPicture();
//            if (StringUtils.isNotBlank(pictureUrl)) {
//                String[] picUrls = pictureUrl.split(";");
//                if (picUrls.length > 0) {
//                    for (int i = 0; i < picUrls.length; i++) {
//                        picUrls[i] = "/feedback/picture/" + picUrls[i];
//                    }
//                    display.setPicture(String.join(";", picUrls));
//                }
//            }
        }else{

            LeaseException.throwSystemException(LeaseExceEnums.ADVERTISEMENT_NOT_EXIST);
        }
        return display;
    }

    @Override
    public List<AdvertisementDisplay> listForManager(String enterpriseId) {
        Manufacturer manufacturer = manufacturerService.selectByEnterpriseId(enterpriseId);
        if(manufacturer == null)LeaseException.throwSystemException(LeaseExceEnums.MANUFACTURER_NOT_EXIST);
        EntityWrapper<AdvertisementDisplay> displayEntityWrapper = new EntityWrapper<AdvertisementDisplay>();
        displayEntityWrapper.eq("sys_user_id",manufacturer.getSysAccountId()).eq("is_deleted", 0)
                .orderBy("sort", true);
        List<AdvertisementDisplay> advertisementDisplayList = selectList(displayEntityWrapper);
        for (AdvertisementDisplay display : advertisementDisplayList) {
            String pictureUrl = display.getPicture();
            if (StringUtils.isNotBlank(pictureUrl)) {
                String[] picUrls = pictureUrl.split(";");
                if (picUrls.length > 0) {
                    for (int i = 0; i < picUrls.length; i++) {
                        picUrls[i] = "/feedback/picture/" + picUrls[i];
                    }
                    display.setPicture(String.join(";", picUrls));
                }
            }
        }
        return advertisementDisplayList;
    }


    @Override
    public boolean delete(List<Integer> id) {
        AdvertisementDisplay display = new AdvertisementDisplay();
        display.setUtime(new Date());
        display.setIsDeleted(1);
        EntityWrapper<AdvertisementDisplay> entityWrapper = new EntityWrapper<>();
        entityWrapper.in("id", id);
        return update(display, entityWrapper);
    }

    @Override
    public AdvertisementDisplay getById(Integer id) {

        AdvertisementDisplay display = selectOne(new EntityWrapper<AdvertisementDisplay>().eq("id", id).eq("is_deleted", 0));
        if (ParamUtil.isNullOrEmptyOrZero(display)) {
            LeaseException.throwSystemException(LeaseExceEnums.ENTITY_NOT_EXISTS);
        }
        return display;
    }

    @Override
    public Page<AdvertisementDisplay> list(Pageable<AdvertisementQueryDto> pageable) {
        AdvertisementQueryDto queryDto=pageable.getQuery();
        queryDto.setCurrent((pageable.getCurrent()-1)*pageable.getSize());
        queryDto.setSize(pageable.getSize());
        Page<AdvertisementDisplay> page=new Page<>();
        List<AdvertisementDisplay> list=new ArrayList<>();
        list=advertisementDisplayDao.selectAdvertPage(queryDto);
        if(ParamUtil.isNullOrEmptyOrZero(list)){
            return page;
        }
        Integer total=advertisementDisplayDao.selectAdvertPageCount(queryDto);
        page.setRecords(list);
        page.setTotal(total);
        return page;
    }

}
