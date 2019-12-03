package com.gizwits.lease.product.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Dto - 品类更新
 *
 * @author lilh
 * @date 2017/7/18 14:39
 */
public class ProductCategoryForUpdateDto {

    @NotNull
    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    @ApiModelProperty("品类ID")
    private Integer categoryId;


    @ApiModelProperty(" 安全库存数量")
    private Integer categoryCount;


    @ApiModelProperty(" 产品型号")
    private String categoryType;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(Integer categoryCount) {
        this.categoryCount = categoryCount;
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
