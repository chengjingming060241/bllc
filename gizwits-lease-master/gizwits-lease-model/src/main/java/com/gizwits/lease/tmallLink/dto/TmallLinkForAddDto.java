package com.gizwits.lease.tmallLink.dto;

import com.gizwits.lease.product.dto.ProductCommandConfigForAddDto;
import com.gizwits.lease.product.dto.ProductDataPointDto;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Dto - 添加产品品类
 *
 * @author lilh
 * @date 2017/7/19 19:18
 */
public class TmallLinkForAddDto {

    @ApiModelProperty("链接名称")
    private String linkName;

    @ApiModelProperty("产品型号")
    private String categoryId;

    @ApiModelProperty("链接地址")
    private String linkUrl;

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
}
