package com.gizwits.lease.enums;

/**
 * Description:
 * User: yinhui
 * Date: 2018-06-19
 */
public enum  OrderEndType {

    END_BY_SNOTI(1,"正常结束"),
    END_BY_JOB(2,"定时器结束"),
    ;

    Integer code;
    String desc;

    OrderEndType(Integer code, String desc) {
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
