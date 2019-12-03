package com.gizwits.lease.card.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class ReceiveCardDto {

    @ApiModelProperty("卡券ID")
    @NotNull(message = "cardId may not be null")
    private String cardId;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
