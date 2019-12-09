package com.gizwits.lease.tmallLink.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gizwits.boot.annotation.Query;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * QueryDto - 天猫链接查询dto
 *
 */
public class TmallLinkQueryDto {

    @Query(field = "link_name",operator = Query.Operator.like)
    private String linkName;

    @Query(field = "category_name", operator = Query.Operator.like)
    private String categoryName;

    @Query(field = "ctime", operator = Query.Operator.ge)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startUpdateTime;

    @Query(field = "ctime", operator = Query.Operator.le)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endUpdateTime;

    @JsonIgnore
    @Query(field = "id", operator = Query.Operator.in)
    private List<Integer> ids;

    @JsonIgnore
    @Query(field = "is_deleted")
    private Integer isDeleted;

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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
