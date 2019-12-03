package com.gizwits.lease.device.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class ManageDeviceDetailDto extends DeviceShowDto {

    @ApiModelProperty("经度")
    private BigDecimal longitude;

    @ApiModelProperty("维度")
    private BigDecimal latitude;

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
}
