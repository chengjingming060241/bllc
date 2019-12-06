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
 * Dto - 产品详情
 *
 * @author lilh
 * @date 2017/7/20 10:54
 */
public class TmallLinkForDetailDto {

    private Integer id;

    private String name;

    private Integer categoryId;

    private String categoryName;

    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date ctime;

    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date utime;

    /**
     * 描述
     */
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    // --- 用于更新的下拉列表
    /** 品牌下拉列表 */
    private List<ProductCategoryForPullDto> productCategories = new ArrayList<>();


    public TmallLinkForDetailDto() {
    }

    public TmallLinkForDetailDto(TmallLink tmallLink) {
        BeanUtils.copyProperties(tmallLink, this);
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
