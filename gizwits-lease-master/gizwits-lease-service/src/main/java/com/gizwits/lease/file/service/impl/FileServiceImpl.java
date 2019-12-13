package com.gizwits.lease.file.service.impl;

import com.gizwits.boot.common.MessageCodeConfig;
import com.gizwits.boot.exceptions.SysExceptionEnum;
import com.gizwits.boot.exceptions.SystemException;
import com.gizwits.boot.utils.PictureUtils;
import com.gizwits.boot.utils.SysConfigUtils;
import com.gizwits.lease.exceptions.LeaseExceEnums;
import com.gizwits.lease.exceptions.LeaseException;
import com.gizwits.lease.file.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Description:
 * Created by Sunny on 2019/9/17 18:49
 */
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Override
    public String uploadImg(MultipartFile file) {
        String filename = file.getOriginalFilename();
        log.info("上传文件，fileName = " + filename);
        //判断是否为限制文件类型
        if (!PictureUtils.checkFile(filename)) {
            //限制文件类型，
            throw new SystemException(SysExceptionEnum.PICTURE_SUFFIX_ERROR.getCode(), SysExceptionEnum.PICTURE_SUFFIX_ERROR.getMessage());
        }
        //限制文件大小
        long size = file.getSize();
        if(!PictureUtils.checkSize(size)){
            throw new SystemException(SysExceptionEnum.PICTURE_OUT_OF_SIZE.getCode(), SysExceptionEnum.PICTURE_OUT_OF_SIZE.getMessage());
        }

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        String newFilename = UUID.randomUUID() + suffix;
        log.info("新文件名 = " + newFilename);
        String uri = null;
        try {
            MessageCodeConfig messageCodeConfig = SysConfigUtils.get(MessageCodeConfig.class);
//            String path = System.getProperty("user.dir")+"/data/lease/files/images/";
            String path="/data/lease/files/avatar/" + newFilename;
//
            log.info("图片路径： = " + path);
            File f = new File(path);
            File parentFile = f.getParentFile();
            if (!parentFile.exists()){
                log.info("Make dir!!!");
                parentFile.mkdirs();
            }
            file.transferTo(f);
            uri = parentFile.getName()+"/"+f.getName();
            log.info("uri = " + uri);
        } catch (IOException e) {
            log.error("图片文件上传失败，原因：" + e);
            LeaseException.throwSystemException(LeaseExceEnums.UPLOAD_IMAGE_FAIL);
        }
        return "/avatar/"+newFilename;
    }
}
