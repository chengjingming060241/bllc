package com.gizwits.lease.card.dto;

import com.gizwits.boot.sys.entity.SysUserExt;

import java.util.List;

public class CardSyncActionDto {
    private CardSyncDto syncStatus;
    private Boolean needSync;
    private SysUserExt sysUserExt;
    private List<String> cardIdList;

    public CardSyncActionDto(CardSyncDto syncStatus) {
        this.syncStatus = syncStatus;
        needSync = false;
    }

    public CardSyncDto getSyncStatus() {
        return syncStatus;
    }

    public void setSyncStatus(CardSyncDto syncStatus) {
        this.syncStatus = syncStatus;
    }

    public Boolean getNeedSync() {
        return needSync;
    }

    public void setNeedSync(Boolean needSync) {
        this.needSync = needSync;
    }

    public SysUserExt getSysUserExt() {
        return sysUserExt;
    }

    public void setSysUserExt(SysUserExt sysUserExt) {
        this.sysUserExt = sysUserExt;
    }

    public List<String> getCardIdList() {
        return cardIdList;
    }

    public void setCardIdList(List<String> cardIdList) {
        this.cardIdList = cardIdList;
    }
}
