package com.gizwits.lease.enums;

/**
 * 退款方式
 * Created by yuqing on 2017/10/26.
 */
public enum RefundMethod {

    WEIXIN(1, "微信"),

    ALIPAY(2, "支付宝"),

    CARD(3, "充值卡");

    Integer code;
    String msg;

    RefundMethod(int key , String msg) {
        this.code = key;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
