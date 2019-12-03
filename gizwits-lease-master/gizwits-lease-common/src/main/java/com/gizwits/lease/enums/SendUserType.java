package com.gizwits.lease.enums;

/**
 * Description:发送消息用户
 * User: yinhui
 * Date: 2018-01-23
 */
public enum SendUserType {

    NOT_ALL(0,"不是全部用户"),
    ALL_SYS_USER(1,"全部系统用户"),
    ALL_WECHAT_USER(2,"全部微信用户"),
    ALL_APP_USER(3,"全部app用户"),
    ;
    Integer code;
    String desc;

    SendUserType(Integer code, String desc) {
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
