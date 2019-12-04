package com.gizwits.lease.constant;

import org.apache.commons.collections.map.HashedMap;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 正常、故障、报警、窜货报警
 * 0.待扫码、1.可入库
 */
public enum DeviceSweepCodeStatus {
    PENDING_CODE(0,"待扫码"),
    WAIT_TO_ENTRY(1, "待入库"),
    To_Be_But_Bf_Stock(2,"已入库"),
    Out_of_stock(3,"已出库");
    Integer code;
    String name;

    private static Map<Integer, String> codeToName;

    static {
        codeToName = Arrays.stream(DeviceSweepCodeStatus.values()).collect(Collectors.toMap(item -> item.code, item -> item.name));
    }

    DeviceSweepCodeStatus(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Map enumToMap() {
        DeviceSweepCodeStatus[] deviceStatuses = DeviceSweepCodeStatus.class.getEnumConstants();
        Map deviceMap = new HashedMap();
        for (DeviceSweepCodeStatus deviceStatus : deviceStatuses) {
            deviceMap.put(deviceStatus.getCode(), deviceStatus.getName());
        }
        return deviceMap;
    }

    public static String getName(Integer code) {
        return codeToName.get(code);
    }

    public static String getShowName(Integer workStatus, Integer faultStatus, Boolean lock) {
        if (lock) {
            return PENDING_CODE.name;
        }
        if (faultStatus.equals(WAIT_TO_ENTRY.getCode())) {
            return WAIT_TO_ENTRY.name;
        }
        if (faultStatus.equals(To_Be_But_Bf_Stock.getCode())) {
            return To_Be_But_Bf_Stock.name;
        }
        if (faultStatus.equals(Out_of_stock.getCode())) {
            return Out_of_stock.name;
        }
        return codeToName.get(workStatus);
    }
}