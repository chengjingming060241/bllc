package com.gizwits.lease.device.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class ManageDeviceQueryDto {

    @ApiModelProperty(value = "设备序列号", required = true)
    @NotNull(message = "sno may not be null")
    private String sno;

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }
}
