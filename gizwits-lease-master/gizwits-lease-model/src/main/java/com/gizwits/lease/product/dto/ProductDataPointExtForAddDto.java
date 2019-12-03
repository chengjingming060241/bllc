package com.gizwits.lease.product.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author yuqing
 * @date 2018-02-05
 */
@ApiModel("扩展指令")
public class ProductDataPointExtForAddDto {

    /**
     * 产品id
     */
    @ApiModelProperty("产品id")
    @NotNull
    private Integer productId;

    /**
     * 数据点id，product_command_config.id
     */
    @ApiModelProperty("数据点id")
    @NotNull
    private Integer dataId;

    /**
     * 指令名称
     */
    @ApiModelProperty("指令名称")
    @NotBlank
    private String name;

    /**
     * 是否在后台展示：0,不展示；1,展示
     */
    @ApiModelProperty("是否展示在前端")
    @NotNull
    private Boolean showEnable;

    /**
     * 第三方api提供商，0：无；1：和风；2：阿里
     */
    @ApiModelProperty("API接口提供商，1：和风，2：阿里")
    @NotNull
    @Range(min = 1, max = 4)
    private Integer vendor;

    /**
     * 1：温度；2：湿度；3：pm2.5；4：空气质量
     */
    @ApiModelProperty("参数，1：室外温度；2：湿度；3：pm2.5；4：空气质量")
    @NotNull
    @Range(min = 1, max = 4)
    private Integer param;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getDataId() {
        return dataId;
    }

    public void setDataId(Integer dataId) {
        this.dataId = dataId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getShowEnable() {
        return showEnable;
    }

    public void setShowEnable(Boolean showEnable) {
        this.showEnable = showEnable;
    }

    public Integer getVendor() {
        return vendor;
    }

    public void setVendor(Integer vendor) {
        this.vendor = vendor;
    }

    public Integer getParam() {
        return param;
    }

    public void setParam(Integer param) {
        this.param = param;
    }
}
