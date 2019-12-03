package com.gizwits.lease.enums;

/**
 * Description:
 * User: yinhui
 * Date: 2018-01-17
 */
public enum RateType {
    PRECENT(1,"百分比"),
    NUM(2,"数值"),
    ;
     Integer code;
     String desc;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    RateType(Integer code, String desc) {

        this.code = code;
        this.desc = desc;
    }
}
