package com.gizwits.lease.card.dto;

import com.gizwits.boot.annotation.Query;
import io.swagger.annotations.ApiModelProperty;

public class CardListQueryDto {

    @ApiModelProperty("卡券名称")
    @Query(field = "title", operator = Query.Operator.like)
    private String title;

    @ApiModelProperty("卡券类型 CASH:代金券, DISCOUNT:折扣券,年卡:YEAR,体验卡:TRY")
    @Query(field = "card_type")
    private String cardType;

    @ApiModelProperty("卡券是否投放")
    private Boolean dispatched;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Boolean getDispatched() {
        return dispatched;
    }

    public void setDispatched(Boolean dispatched) {
        this.dispatched = dispatched;
    }
}
