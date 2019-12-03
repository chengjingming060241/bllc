package com.gizwits.lease.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description:
 * User: yinhui
 * Date: 2018-06-05
 */
public enum ShareBenefitPayType {
    WEIXIN("WEIXIN", "微信支付"),
    ALIPAY("ALIPAY", "支付宝支付"),
    ;

    private String code;
    private String desc;
    static Map<String, String> map;

    static {
        map = Arrays.stream(ShareBenefitPayType.values()).collect(Collectors.toMap(item->item.code,item->item.desc));
    }

    ShareBenefitPayType(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static Map<String, String> getMap() {
        return map;
    }

    public static void setMap(Map<String, String> map) {
        ShareBenefitPayType.map = map;
    }
}
