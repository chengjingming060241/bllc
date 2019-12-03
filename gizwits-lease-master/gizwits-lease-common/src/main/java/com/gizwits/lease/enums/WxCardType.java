package com.gizwits.lease.enums;

public enum  WxCardType {
    DISCOUNT("DISCOUNT", "折扣券"),
    CASH("CASH", "代金券"),
    ;
    private String code;
    private String message;

    WxCardType(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
