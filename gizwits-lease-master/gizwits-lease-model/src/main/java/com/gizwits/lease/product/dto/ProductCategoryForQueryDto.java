package com.gizwits.lease.product.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gizwits.boot.annotation.Query;
import io.swagger.annotations.ApiModelProperty;

/**
 * Dto - 品类列表页查询
 *
 * @author lilh
 * @date 2017/7/18 11:35
 */
public class ProductCategoryForQueryDto {

    @Query(field = "name", operator = Query.Operator.like)
    private String name;

    @Query(field = "category_type", operator = Query.Operator.like)
    private String categoryType;

    @JsonIgnore
    @Query(field = "sys_user_id", operator = Query.Operator.in)
    private List<Integer> accessableUserIds;

    @JsonIgnore
    @Query(field = "id", operator = Query.Operator.in)
    private List<Integer> ids;

    @JsonFormat
    @Query(field = "is_deleted")
    private Integer isDeleted=0;

    @ApiModelProperty("修改时间开始")
    private Date upTimeStart;

    @ApiModelProperty("修改时间结束")
    private Date upTimeEnd;

    public String getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(String categoryType) {
        this.categoryType = categoryType;
    }

    public Date getUpTimeStart() {
        return upTimeStart;
    }

    public void setUpTimeStart(Date upTimeStart) {
        this.upTimeStart = upTimeStart;
    }

    public Date getUpTimeEnd() {
        return upTimeEnd;
    }

    public void setUpTimeEnd(Date upTimeEnd) {
        this.upTimeEnd = upTimeEnd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getAccessableUserIds() {
        return accessableUserIds;
    }

    public void setAccessableUserIds(List<Integer> accessableUserIds) {
        this.accessableUserIds = accessableUserIds;
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
