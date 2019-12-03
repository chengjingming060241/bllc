package com.gizwits.lease.user.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Date: 2018-02-28
 */

public class GizwitsTokenDto {

    @NotBlank(message = "appid 不能为空")
    @ApiModelProperty("机智云产品appid")
    private String appid;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}

