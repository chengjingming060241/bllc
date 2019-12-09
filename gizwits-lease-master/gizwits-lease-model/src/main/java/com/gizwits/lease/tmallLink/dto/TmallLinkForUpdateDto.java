package com.gizwits.lease.tmallLink.dto;

import com.gizwits.lease.product.dto.ProductDataPointForUpdateDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Dto - 产品更新
 *
 * @author lilh
 * @date 2017/7/20 14:03
 */
public class TmallLinkForUpdateDto {

    @NotNull
    private Integer id;

    @NotBlank
    @ApiModelProperty("链接名称")
    private String linkName;

    @NotNull
    @ApiModelProperty("产品id")
    private Integer categoryId;

    @NotBlank
    @ApiModelProperty("链接地址")
    private String linkUrl;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }


}
