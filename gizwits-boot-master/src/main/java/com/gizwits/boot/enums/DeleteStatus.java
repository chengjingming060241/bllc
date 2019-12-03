package com.gizwits.boot.enums;

/**
 * Enum - 删除状态
 *
 * @author lilh
 * @date 2017/7/20 15:19
 */
public enum DeleteStatus {

    NOT_DELETED(0, "未删除"),
    DELETED(1, "已删除");

    private Integer code;

    private String desc;

    DeleteStatus(Integer code, String desc) {
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
