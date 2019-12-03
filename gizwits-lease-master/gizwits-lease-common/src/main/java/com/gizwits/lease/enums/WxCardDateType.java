package com.gizwits.lease.enums;

public enum  WxCardDateType {
    DATE_TYPE_FIX_TIME_RANGE("DATE_TYPE_FIX_TIME_RANGE", "固定日期区间"),
    DATE_TYPE_FIX_TERM("DATE_TYPE_FIX_TERM", "固定时长（自领取后按天算）"),
            ;

    private String code;
    private String message;

    WxCardDateType(String code, String message) {
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
