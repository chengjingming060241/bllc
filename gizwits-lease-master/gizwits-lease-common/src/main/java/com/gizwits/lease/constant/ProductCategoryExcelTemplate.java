package com.gizwits.lease.constant;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * @author lilh
 * @date 2017/8/31 15:29
 */
public class ProductCategoryExcelTemplate {

    @ApiModelProperty("产品名称")
    @NotBlank
    private String name;

    @ApiModelProperty(" 安全库存数量")
    private Integer categoryCount;


    @ApiModelProperty(" 产品型号")
    private String categoryType;


    public ProductCategoryExcelTemplate() {
    }

    public ProductCategoryExcelTemplate(String name,  String categoryType, String categoryCount) {
        this.name = name;
        Number num = Float.parseFloat(categoryCount) * 10;
        this.categoryCount = num.intValue()/10;
        this.categoryType = categoryType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
