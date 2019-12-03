package com.gizwits.lease.device.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class UserDeviceQueryDto {

    @ApiModelProperty("用户ID")
    @NotNull(message = "userId may not be null")
    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
