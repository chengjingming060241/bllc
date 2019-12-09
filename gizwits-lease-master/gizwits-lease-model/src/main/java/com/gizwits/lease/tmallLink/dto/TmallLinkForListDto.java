package com.gizwits.lease.tmallLink.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gizwits.boot.base.Constants;
import com.gizwits.lease.product.entity.Product;
import com.gizwits.lease.tmallLink.entity.TmallLink;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * Dto - 天猫链接列表
 *
 * @author lilh
 * @date 2017/7/18 17:03
 */
public class TmallLinkForListDto {

    /** 品类id */
    private Integer id;

    @ApiModelProperty("链接名称")
    private String linkName;

    @ApiModelProperty("产品名称")
    private String categoryName;

    @ApiModelProperty("链接地址")
    private String linkUrl;

    /** 更新时间 */
    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date utime;


    public TmallLinkForListDto(TmallLink tmallLink) {
        BeanUtils.copyProperties(tmallLink, this);
    }

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

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    @Override
    public String toString() {
        return "TmallLinkForListDto{" +
                "id=" + id +
                ", linkName='" + linkName + '\'' +
                ", categoryName='" + categoryName + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", utime=" + utime +
                '}';
    }
}
