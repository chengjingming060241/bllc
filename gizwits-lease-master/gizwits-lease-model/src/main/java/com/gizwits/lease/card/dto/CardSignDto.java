package com.gizwits.lease.card.dto;

public class CardSignDto {
    private String apiTicket;
    private String timestamp;
    private String nonceStr;
    private String cardId;
    private String signature;

    public String getApiTicket() {
        return apiTicket;
    }

    public void setApiTicket(String apiTicket) {
        this.apiTicket = apiTicket;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }
}
