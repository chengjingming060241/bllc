package com.gizwits.lease.product.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Dto - 添加产品品类
 *
 * @author lilh
 * @date 2017/7/19 19:18
 */
public class ProductForAddDto {

    @NotBlank
    @ApiModelProperty("品类名称")
    private String name;

    @NotNull
    private Integer manufacturerAccountId;

    @NotBlank
    @ApiModelProperty("ProductKey")
    private String gizwitsProductKey;

    @NotBlank
    @ApiModelProperty("ProductSecret")
    private String gizwitsProductSecret;

    private String gizwitsEnterpriseId;

    private String gizwitsEnterpriseSecret;

    @NotBlank
    @ApiModelProperty("产品授权id")
    private String authId;

    @NotBlank
    @ApiModelProperty("产品授权密钥")
    private String authSecret;

    @NotBlank
    private String qrcodeType;

    @NotBlank
    private String locationType;

    @NotBlank
    @ApiModelProperty("机智云AppId")
    private String gizwitsAppId;

    @NotBlank
    @ApiModelProperty("机智云AppSecret")
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

    /** 数据点 */
    private List<ProductDataPointDto> productDataPoints = new ArrayList<>();

    /** 指令 */
    private List<ProductCommandConfigForAddDto> productCommands = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<ProductDataPointDto> getProductDataPoints() {
        return productDataPoints;
    }

    public void setProductDataPoints(List<ProductDataPointDto> productDataPoints) {
        this.productDataPoints = productDataPoints;
    }

    public List<ProductCommandConfigForAddDto> getProductCommands() {
        return productCommands;
    }

    public void setProductCommands(List<ProductCommandConfigForAddDto> productCommands) {
        this.productCommands = productCommands;
    }
}
