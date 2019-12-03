package com.gizwits.lease.product.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Dto - 添加产品
 *
 * @author lilh
 * @date 2017/7/18 11:46
 */
public class ProductCategoryForAddDto {

    @ApiModelProperty("产品名称")
    @NotBlank
    private String name;

    @NotNull
    @ApiModelProperty("品类ID")
    private Integer categoryId;


    @ApiModelProperty("品类名称")
    private String categoryName;

    @ApiModelProperty("产品描述")
    private String remark;

    @ApiModelProperty(" 安全库存数量")
    private Integer categoryCount;


    @ApiModelProperty(" 产品型号")
    private String categoryType;

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public Integer getCategoryCount() {
        return categoryCount;
    }

    public void setCategoryCount(Integer categoryCount) {
        this.categoryCount = categoryCount;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    /** 父品类 */
    private Integer parentCategoryId;


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}
