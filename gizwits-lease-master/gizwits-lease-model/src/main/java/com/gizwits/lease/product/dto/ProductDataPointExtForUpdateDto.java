package com.gizwits.lease.product.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

/**
 * @author yuqing
 * @date 2018-02-05
 */
public class ProductDataPointExtForUpdateDto extends ProductDataPointExtForAddDto {

    @NotNull
    @ApiModelProperty("扩展指令id")
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
