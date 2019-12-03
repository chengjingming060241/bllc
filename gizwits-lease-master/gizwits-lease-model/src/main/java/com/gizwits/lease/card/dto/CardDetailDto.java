package com.gizwits.lease.card.dto;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModelProperty;

public class CardDetailDto {

    @ApiModelProperty("卡券详情")
    private JSONObject card;

    public CardDetailDto(JSONObject card) {
        this.card = card;
    }

    public JSONObject getCard() {
        return card;
    }

    public void setCard(JSONObject card) {
        this.card = card;
    }
}
