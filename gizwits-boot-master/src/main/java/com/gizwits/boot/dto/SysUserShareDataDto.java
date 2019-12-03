package com.gizwits.boot.dto;

import io.swagger.annotations.ApiModelProperty;

public class SysUserShareDataDto {

    @ApiModelProperty("是否共享产品收费模式")
    private Boolean productServiceMode;

    public Boolean getProductServiceMode() {
        return productServiceMode;
    }

    public void setProductServiceMode(Boolean productServiceMode) {
        this.productServiceMode = productServiceMode;
    }
}
