package com.gizwits.lease.device.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Description:移动端更新设备经纬度信息
 * User: yinhui
 * Date: 2018-05-25
 */
public class AppUpdateDeviceLocationDto implements Serializable {

    /**
     * 经度
     */
    @ApiModelProperty("经度")
    private BigDecimal longitude;
    /**
     * 维度
     */
    @ApiModelProperty("纬度")
    private BigDecimal latitude;

    @ApiModelProperty("设备mac地址")
    private String mac;

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }
}
