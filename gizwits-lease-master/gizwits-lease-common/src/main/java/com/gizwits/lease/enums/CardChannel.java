package com.gizwits.lease.enums;

public enum CardChannel {
    // 投放渠道 1:微信 2:app
    WECHAT(1, "微信"),
    APP(2, "App"),
    ;
    private Integer code;
    private String message;

    CardChannel(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
