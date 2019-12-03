package com.gizwits.boot.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Enum - 系统帐号状态
 *
 * @author lilh
 * @date 2017/7/11 11:35
 */
public enum SysUserStatus {

    DISABLE(0, "禁用"),
    ENABLE(1, "启用");

    private Integer code;

    private String desc;


    private static Map<Integer, String> map;

    static {
        map = Arrays.stream(SysUserStatus.values()).collect(Collectors.toMap(item -> item.code, item -> item.desc));
        //Arrays.stream(SysUserStatus.values()).forEach(item -> map.put(item.code, item.desc));
    }

    SysUserStatus(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static Map<Integer, String> getMap() {
        return map;
    }

    public static String getDesc(Integer code) {
        return map.get(code);
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
