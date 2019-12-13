package com.gizwits.lease.file.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Description:
 * Created by Sunny on 2019/9/17 18:48
 */
public interface FileService {

    /**
     * 上传文件--头像
     * @param file
     */
    String uploadImg(MultipartFile file);
}
