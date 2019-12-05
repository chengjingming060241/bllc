package com.gizwits.lease.stat.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by GaGi on 2017/7/19.
 */

public class StatDeviceDto {

    @ApiModelProperty("产品id")
    private Integer productId;

    @ApiModelProperty("省份，查询省份下城市时使用")
    private String province;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
