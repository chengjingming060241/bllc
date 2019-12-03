package com.gizwits.boot.enums;

/**
 * Created by zhl on 2018/2/1.
 */
public enum SysUserType {
    NORMAL(0, "普通系统用户"),
    SUPERADMIN(1, "超级管理员"),
    MANUFACTURER(2,"厂商管理员"),
    AGENT(3,"经销商"),
    OPERATOR(4,"操作员"),
    CLERK(5,"入库员")
    ;

    private Integer code;

    private String desc;

    SysUserType(Integer code, String desc) {
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
