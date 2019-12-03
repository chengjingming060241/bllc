package com.gizwits.lease.card.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.UUID;

public class CardSyncDto implements Serializable {

    @ApiModelProperty("同步ID")
    private String syncId;

    @ApiModelProperty("同步是否完成")
    private Boolean finished;

    @ApiModelProperty("同步进度（0 ~ 1）")
    private Float progress;

    public CardSyncDto() {
        syncId = UUID.randomUUID().toString().replaceAll("-", "");
        finished = false;
        progress = 0.0f;
    }

    public String getSyncId() {
        return syncId;
    }

    public void setSyncId(String syncId) {
        this.syncId = syncId;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    public Float getProgress() {
        return progress;
    }

    public void setProgress(Float progress) {
        this.progress = progress;
    }
}
