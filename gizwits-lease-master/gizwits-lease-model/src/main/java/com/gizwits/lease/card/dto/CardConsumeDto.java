package com.gizwits.lease.card.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class CardConsumeDto {

    @ApiModelProperty("卡券ID")
    @NotNull(message = "cardId may not be null")
    private String cardId;

    @ApiModelProperty("卡券Code")
    @NotNull(message = "cardCode may not be null")
    private String cardCode;

    @ApiModelProperty("订单号")
    @NotNull(message = "orderNo may not be null")
    private String orderNo;

    public CardConsumeDto(String cardId, String cardCode, String orderNo) {
        this.cardId = cardId;
        this.cardCode = cardCode;
        this.orderNo = orderNo;
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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
