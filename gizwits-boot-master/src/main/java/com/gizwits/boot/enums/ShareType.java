package com.gizwits.boot.enums;

/**
 * Enum - 角色类型
 *
 * @author lilh
 * @date 2017/7/7 14:42
 */
public enum ShareType {

    NOT_SHARE(0, "不共享"),
    SHARE(1, "共享");

    /** 编码 */
    private int code;

    /** 描述 */
    private String desc;

    ShareType(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

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

}
