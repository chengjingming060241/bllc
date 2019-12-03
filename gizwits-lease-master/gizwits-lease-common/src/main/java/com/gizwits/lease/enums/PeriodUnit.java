package com.gizwits.lease.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum PeriodUnit {
    YEAR (1, "年"),
    MONTH (2, "月"),
    ;

    private final int code;
    private final String message;

    PeriodUnit(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private static Map<Integer, String> codeToMessage;

    static {
        codeToMessage = Arrays.stream(PeriodUnit.values()).collect(Collectors.toMap(item -> item.code, item -> item.message));
    }

    public static String getMessage(int code) {
        return codeToMessage.get(code);
    }
}
