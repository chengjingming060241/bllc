package com.gizwits.lease.share.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author yuqing
 * @date 2018-05-24
 */
public class SavePictureResponse {

    @ApiModelProperty("图片保存后的路径")
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
