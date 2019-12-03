package com.gizwits.lease.card.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Range;

public class DispatchedCardQueryDto {

    /**
     * @see com.gizwits.lease.enums.CardChannel
     */
    @ApiModelProperty("投放渠道 1:微信 2:app")
    @Range(min = 1, max = 2)
    private Integer channel;

    public Integer getChannel() {
        return channel;
    }

    public void setChannel(Integer channel) {
        this.channel = channel;
    }
}
