package com.gizwits.lease.enums;

public enum UserCardStatus {
    // 卡券状态 1:正常 2:已使用 3:已失效
    NORMAL(1, "正常"),
    CONSUMED(2, "已使用"),
    INVALID(3, "已失效"),
    ;
    private Integer code;
    private String message;

    UserCardStatus(Integer code, String message) {
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
