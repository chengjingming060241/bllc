package com.gizwits.lease.enums;

public enum UserInfoState {
    // 用户信息状态 1:由用户编辑的用户信息 2:从第三方获取的用户信息
    SELF(1), THIRD(2);

    private final int value;

    UserInfoState(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
