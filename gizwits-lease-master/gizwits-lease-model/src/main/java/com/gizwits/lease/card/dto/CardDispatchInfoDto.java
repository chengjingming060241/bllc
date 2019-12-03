package com.gizwits.lease.card.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CardDispatchInfoDto {
    @ApiModelProperty(value = "卡券ID", required = true)
    @NotNull(message = "cardId may not be null")
    private String cardId;

    @ApiModelProperty(value = "微信投放 0:否, 1:是", required = true)
    @NotNull(message = "dispatchWeb may not be null")
    private Integer dispatchWeb;

    @ApiModelProperty(value = "APP投放 0:否, 1:是", required = true)
    @NotNull(message = "dispatchApp may not be null")
    private Integer dispatchApp;

    @ApiModelProperty("卡券封面")
    private String cover;

    @ApiModelProperty("卡券展示顺序")
    private Integer sequence;

    @ApiModelProperty("卡券适用产品")
    private CardLimitProductDto limitProduct;

    @ApiModelProperty("卡券适用运营商列表")
    private List<CardLimitOperatorDto> limitOperatorList;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Integer getDispatchWeb() {
        return dispatchWeb;
    }

    public void setDispatchWeb(Integer dispatchWeb) {
        this.dispatchWeb = dispatchWeb;
    }

    public Integer getDispatchApp() {
        return dispatchApp;
    }

    public void setDispatchApp(Integer dispatchApp) {
        this.dispatchApp = dispatchApp;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Integer getSequence() {
        return sequence;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public CardLimitProductDto getLimitProduct() {
        return limitProduct;
    }

    public void setLimitProduct(CardLimitProductDto limitProduct) {
        this.limitProduct = limitProduct;
    }

    public List<CardLimitOperatorDto> getLimitOperatorList() {
        return limitOperatorList;
    }

    public void setLimitOperatorList(List<CardLimitOperatorDto> limitOperatorList) {
        this.limitOperatorList = limitOperatorList;
    }
}
