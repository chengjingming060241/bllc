package com.gizwits.lease.constant;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


public enum UserWalletUseEnum{
        RECHARGE(1,"充值"),
        PAY(2,"支付"),;
        Integer code;
        String name;

        UserWalletUseEnum(Integer code, String name) {
            this.code = code;
            this.name = name;
        }

        public static Map enumToMap(){
            UserWalletUseEnum[] userWalletEnums = UserWalletUseEnum.class.getEnumConstants();
            Map map =new HashedMap();
            for (UserWalletUseEnum userWalletEnum: userWalletEnums){
                map.put(userWalletEnum.getCode(),userWalletEnum.getName());
            }
            return map;
        }

        public static UserWalletUseEnum getUserWalletEnum(int code) {
            for (UserWalletUseEnum userWalletEnum : UserWalletUseEnum.values()) {
                if (userWalletEnum.getCode() == code)
                    return userWalletEnum;
            }
            return null;
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
    }
