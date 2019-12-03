package com.gizwits.lease.product.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gizwits.boot.base.Constants;
import com.gizwits.lease.product.entity.ProductCategory;
import io.swagger.annotations.ApiModelProperty;

/**
 * Dto - 产品列表
 *
 * @author lilh
 * @date 2017/7/18 11:31
 */
public class ProductCategoryForListDto {

    private Integer id;

    private String name;


    private Integer productCount = 0;

    private String categoryName;

    @ApiModelProperty(" 产品型号")
    private String categoryType;

    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date utime;

    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date ctime;

    public ProductCategoryForListDto(ProductCategory productCategory) {
        this.id = productCategory.getId();
        this.name = productCategory.getName();
        this.utime = productCategory.getUtime();
        this.ctime = productCategory.getCtime();
        this.categoryType = productCategory.getCategoryType();
    }

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}
