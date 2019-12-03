package com.gizwits.lease.product.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Dto - 产品更新
 *
 * @author lilh
 * @date 2017/7/20 14:03
 */
public class ProductForUpdateDto {

    @NotNull
    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    private Integer categoryId;

    @NotBlank
    private String categoryName;

    @NotNull
    private Integer manufacturerAccountId;

    @NotBlank
    private String gizwitsProductKey;

    @NotBlank
    private String gizwitsProductSecret;

    @NotBlank
    private String gizwitsEnterpriseId;

    @NotBlank
    private String gizwitsEnterpriseSecret;

    @NotBlank
    private String authId;

    @NotBlank
    private String authSecret;

    @NotBlank
    private String qrcodeType;

    @NotBlank
    private String locationType;

    @NotBlank
    private String gizwitsAppId;

    @NotBlank
    private String gizwitsAppSecret;

    private String wxProductId;

    private String networkType;

    @ApiModelProperty("描述")
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getNetworkType() {
        return networkType;
    }

    public void setNetworkType(String networkType) {
        this.networkType = networkType;
    }

    private List<ProductDataPointForUpdateDto> dataPoints = new ArrayList<>();

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

    public Integer getManufacturerAccountId() {
        return manufacturerAccountId;
    }

    public void setManufacturerAccountId(Integer manufacturerAccountId) {
        this.manufacturerAccountId = manufacturerAccountId;
    }

    public String getGizwitsProductKey() {
        return gizwitsProductKey;
    }

    public void setGizwitsProductKey(String gizwitsProductKey) {
        this.gizwitsProductKey = gizwitsProductKey;
    }

    public String getGizwitsProductSecret() {
        return gizwitsProductSecret;
    }

    public void setGizwitsProductSecret(String gizwitsProductSecret) {
        this.gizwitsProductSecret = gizwitsProductSecret;
    }

    public String getGizwitsEnterpriseId() {
        return gizwitsEnterpriseId;
    }

    public void setGizwitsEnterpriseId(String gizwitsEnterpriseId) {
        this.gizwitsEnterpriseId = gizwitsEnterpriseId;
    }

    public String getGizwitsEnterpriseSecret() {
        return gizwitsEnterpriseSecret;
    }

    public void setGizwitsEnterpriseSecret(String gizwitsEnterpriseSecret) {
        this.gizwitsEnterpriseSecret = gizwitsEnterpriseSecret;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }

    public String getAuthSecret() {
        return authSecret;
    }

    public void setAuthSecret(String authSecret) {
        this.authSecret = authSecret;
    }

    public String getQrcodeType() {
        return qrcodeType;
    }

    public void setQrcodeType(String qrcodeType) {
        this.qrcodeType = qrcodeType;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getGizwitsAppId() {
        return gizwitsAppId;
    }

    public void setGizwitsAppId(String gizwitsAppId) {
        this.gizwitsAppId = gizwitsAppId;
    }

    public String getGizwitsAppSecret() {
        return gizwitsAppSecret;
    }

    public void setGizwitsAppSecret(String gizwitsAppSecret) {
        this.gizwitsAppSecret = gizwitsAppSecret;
    }

    public String getWxProductId() {
        return wxProductId;
    }

    public void setWxProductId(String wxProductId) {
        this.wxProductId = wxProductId;
    }

    public List<ProductDataPointForUpdateDto> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(List<ProductDataPointForUpdateDto> dataPoints) {
        this.dataPoints = dataPoints;
    }
}
