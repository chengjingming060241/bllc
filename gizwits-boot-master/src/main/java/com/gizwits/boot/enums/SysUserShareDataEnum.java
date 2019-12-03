package com.gizwits.boot.enums;

public enum SysUserShareDataEnum {
    PRODUCT_SERVICE_MODE("productServiceMode", "产品收费模式"),
    ;

    private String code;
    private String desc;

    SysUserShareDataEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
