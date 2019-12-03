package com.gizwits.lease.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description:
 * User: yinhui
 * Date: 2018-01-22
 */
public enum DeviceOriginType {
    INPUT(1,"人工录入"),
    REPORT(2,"云端上报"),
    RANDOM(3,"随机生成"),
    WEXIN(4, "微信扫码")
    ;
    Integer code;
    String desc;

    private static Map<Integer,String> map;

    static {
        map = Arrays.stream(DeviceOriginType.values()).collect(Collectors.toMap(item->item.code,item->item.desc));
    }

    public static String getName(Integer code){
        return map.get(code);
    }
    DeviceOriginType(Integer code, String desc) {
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

    public static Map<Integer, String> getMap() {
        return map;
    }

    public static void setMap(Map<Integer, String> map) {
        DeviceOriginType.map = map;
    }
}
