package com.gizwits.lease.product.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author yuqing
 * @date 2018-02-05
 */
public class ProductDataPointExtNameValuePair {

    public ProductDataPointExtNameValuePair() {

    }

    public ProductDataPointExtNameValuePair(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("值")
    private Integer value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
