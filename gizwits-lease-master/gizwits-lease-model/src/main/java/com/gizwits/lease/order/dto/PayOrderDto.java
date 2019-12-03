package com.gizwits.lease.order.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * Created by GaGi on 2017/7/31.
 */
public class PayOrderDto {
    @NotNull
    private String sno;

    /**收费详情*/
    private Integer productServiceDetailId;

    @Range(min=0, max=9999,message="不在范围之内")
    private Double quantity;

    private Integer serviceModeId;

    /** 数据点标识名 */
    private String name;

    /** 值 */
    private Object value;

    @ApiModelProperty("卡券ID")
    private String cardId;

    @ApiModelProperty("卡券Code")
    private String cardCode;

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public Integer getProductServiceDetailId() {
        return productServiceDetailId;
    }

    public void setProductServiceDetailId(Integer productServiceDetailId) {
        this.productServiceDetailId = productServiceDetailId;
    }

    public Integer getServiceModeId() {
        return serviceModeId;
    }

    public void setServiceModeId(Integer serviceModeId) {
        this.serviceModeId = serviceModeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }
}
