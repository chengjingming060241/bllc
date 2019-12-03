package com.gizwits.boot.utils;

import com.gizwits.boot.common.MessageCodeConfig;

/**
 * Description:
 * User: yinhui
 * Date: 2017-10-26
 */
public class PictureUtils {

    /**
     * 限制上传图片格式
     */
    /**
     * 判断是否为允许的上传文件类型,true表示允许
     */
    public static boolean checkFile(String fileName) {
        //设置允许上传文件类型
        String suffixList = SysConfigUtils.get(MessageCodeConfig.class).getPictureType();
        // 获取文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf(".")
                + 1, fileName.length());
        if (suffixList.contains(suffix.trim().toLowerCase())) {
            return true;
        }
        return false;
    }

    public static boolean checkSize(long size) {
        long max = SysConfigUtils.get(MessageCodeConfig.class).getFileMaxSize();
        if (size <= 0 || size>max){
            return false;
        }
        return true;
    }
}
