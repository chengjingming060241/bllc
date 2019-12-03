package com.gizwits.lease.constant;

import org.apache.commons.collections.map.HashedMap;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


public enum CardType {
        YEAR("YEAR","年卡"),
        TRY("TRY","体验卡"),

        ;
        CardType(String code, String name){
            this.code = code;
            this.name = name;
        }
        String code;
        String name;

        private static Map<String, String> codeToName;

        static {
            codeToName = Arrays.stream(CardType.values()).collect(Collectors.toMap(item -> item.code, item -> item.name));
        }
        public static String getName(String code) {
            return codeToName.get(code);
        }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static CardType getPayType(String code) {
            for (CardType type : CardType.values()) {
                if (type.getCode().equals(code))
                    return type;
            }
            return null;
        }

        public static Map<String,String> enumToMap() {
            CardType[] payTypes = CardType.class.getEnumConstants();
            Map<String,String> payMap = new HashedMap();
            for (CardType payType: payTypes){
                payMap.put(payType.getCode(),payType.getName());
            }
            return payMap;
        }

    }