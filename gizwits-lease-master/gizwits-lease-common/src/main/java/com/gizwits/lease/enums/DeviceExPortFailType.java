package com.gizwits.lease.enums;

/**
 * Description:
 * User: yinhui
 * Date: 2018-07-04
 */
public enum DeviceExPortFailType {

    FILA_DATA_DUPLICATION(1,"文件数据重复"),
    DATA_ERROR_IN_DATABASE(2,"mac地址已导入"),
    ;

    private int code;
    private String desc;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    DeviceExPortFailType(int code, String desc) {

        this.code = code;
        this.desc = desc;
    }
}
