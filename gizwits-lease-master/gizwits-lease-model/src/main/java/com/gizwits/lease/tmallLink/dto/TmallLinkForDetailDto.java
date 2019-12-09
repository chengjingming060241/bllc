package com.gizwits.lease.tmallLink.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gizwits.boot.base.Constants;
import com.gizwits.lease.product.dto.ManufacturerUserDto;
import com.gizwits.lease.product.dto.ProductCategoryForPullDto;
import com.gizwits.lease.product.dto.ProductDataPointDto;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.tmallLink.entity.TmallLink;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Dto - 链接详情
 *
 */
public class TmallLinkForDetailDto {

    private Integer id;

    /**链接名称*/
    private String linkName;

    /**产品id*/
    private String categoryId;

    /**产品型号*/
    private String categoryName;

    /**链接地址*/
    private String linkUrl;


    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date ctime;

    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date utime;


    // --- 用于更新的下拉列表
    /** 产品下拉列表 */
    private List<ProductCategoryForPullDto> productCategories = new ArrayList<>();


    public TmallLinkForDetailDto() {
    }

    public TmallLinkForDetailDto(TmallLink tmallLink) {
        BeanUtils.copyProperties(tmallLink, this);
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public List<ProductCategoryForPullDto> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategoryForPullDto> productCategories) {
        this.productCategories = productCategories;
    }

}
