package com.gizwits.lease.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description:
 * User: yinhui
 * Date: 2018-01-18
 */
public enum  DeviceActiveStatusType {

    UNACTIVE(1,"未激活"),
    ACTIVE(2,"已激活"),
    ;
    Integer code;
    String desc;
    static Map<Integer,String> map;

    static {
        map = Arrays.stream(DeviceActiveStatusType.values()).collect(Collectors.toMap(item->item.code, item->item.desc));
    }

    public static String getName(Integer code){
        return map.get(code);
    }
    DeviceActiveStatusType(Integer code, String desc) {
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
        DeviceActiveStatusType.map = map;
    }
}
