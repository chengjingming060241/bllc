package com.gizwits.lease.share.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author yuqing
 * @date 2018-05-24
 */
public class SavePictureRequest {

    @ApiModelProperty("文件名，后台需要根据此来获取图片格式（后缀），非必填")
    private String fileName;

    @ApiModelProperty("BASE64数据，必填")
    private String data;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
