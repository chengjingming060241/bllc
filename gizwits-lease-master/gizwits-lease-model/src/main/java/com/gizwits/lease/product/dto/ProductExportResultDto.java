package com.gizwits.lease.product.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Description:
 * User: yinhui
 * Date: 2018-07-04
 */
public class ProductExportResultDto implements Serializable{

    @ApiModelProperty("名称、型号")
    private String name;
    @ApiModelProperty("导入失败的原因")
    private String reason;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
