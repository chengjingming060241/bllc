package com.gizwits.lease.enums;

/**
 * 充值页面项目
 * Created by yinhui on 2017/9/27.
 */
public enum RechargeProductType {
    IFRESH(1,"艾芙芮"),
    KALI(2,"卡励"),
    QEK(3,"沁尔康"),
    USER(4,"产品用户端充值卡"),
    ;
    Integer code;
    String desc;

    RechargeProductType(Integer code, String desc) {
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
