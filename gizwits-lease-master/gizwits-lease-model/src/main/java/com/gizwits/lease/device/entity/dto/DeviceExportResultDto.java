package com.gizwits.lease.device.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Description:
 * User: yinhui
 * Date: 2018-07-04
 */
public class DeviceExportResultDto implements Serializable{

    @ApiModelProperty("导入设备时为mac信息，导入分配时为二维码信息")
    private String mac;
    @ApiModelProperty("导入失败的原因")
    private String reason;

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
