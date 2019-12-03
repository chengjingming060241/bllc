package com.gizwits.lease.product.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author yuqing
 * @date 2018-02-05
 */
public class ProductDataPointExtForQueryDto {

    @ApiModelProperty("产品id")
    private Integer productId;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}
