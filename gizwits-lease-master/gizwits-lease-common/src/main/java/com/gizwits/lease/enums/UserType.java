package com.gizwits.lease.enums;

/**
 * Description:
 * User: yinhui
 * Date: 2018-01-19
 */
public enum  UserType {
    SYSTEM_USER(1,"系统用户"),
    USER(2,"微信/app用户"),
    ;
    Integer code;
    String desc;

    UserType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

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
}
