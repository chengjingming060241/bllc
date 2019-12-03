package com.gizwits.lease.product.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gizwits.boot.base.Constants;
import com.gizwits.lease.product.entity.ProductCategory;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

/**
 * Dto - 品类详情
 *
 * @author lilh
 * @date 2017/7/18 14:23
 */
public class ProductCategoryForDetailDto {

    private Integer id;

    private String name;

    private Integer productCount;

    private String categoryName;

    private Integer categoryId;

    @ApiModelProperty(" 安全库存数量")
    private Integer categoryCount;


    @ApiModelProperty(" 产品型号")
    private String categoryType;



    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date ctime;

    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date utime;

    public ProductCategoryForDetailDto(ProductCategory productCategory) {
        BeanUtils.copyProperties(productCategory, this);
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

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }
}
