package com.gizwits.lease.tmallLink.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gizwits.boot.annotation.Query;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * QueryDto - 产品查询dto
 *
 * @author lilh
 * @date 2017/7/5 15:15
 */
public class TmallLinkQueryDto {

    @Query(field = "category_id")
    private Integer productCategoryId;

    @Query(field = "name", operator = Query.Operator.like)
    private String productName;

    @Query(field = "utime", operator = Query.Operator.ge)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startUpdateTime;

    @Query(field = "utime", operator = Query.Operator.le)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endUpdateTime;

    @JsonIgnore
    @Query(field = "id", operator = Query.Operator.in)
    private List<Integer> ids;

    @JsonIgnore
    @Query(field = "is_deleted")
    private Integer isDeleted;

    @JsonIgnore
    @Query(field = "category_type")
    private Integer categoryType;


    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Date getStartUpdateTime() {
        return startUpdateTime;
    }

    public void setStartUpdateTime(Date startUpdateTime) {
        this.startUpdateTime = startUpdateTime;
    }

    public Date getEndUpdateTime() {
        return endUpdateTime;
    }

    public void setEndUpdateTime(Date endUpdateTime) {
        this.endUpdateTime = endUpdateTime;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public void setIds(List<Integer> ids) {
        this.ids = ids;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

}
