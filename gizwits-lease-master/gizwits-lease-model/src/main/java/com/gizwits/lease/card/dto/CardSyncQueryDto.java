package com.gizwits.lease.card.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class CardSyncQueryDto {

    @NotNull(message = "syncId may not be null")
    @ApiModelProperty("同步ID")
    private String syncId;

    public String getSyncId() {
        return syncId;
    }

    public void setSyncId(String syncId) {
        this.syncId = syncId;
    }
}
