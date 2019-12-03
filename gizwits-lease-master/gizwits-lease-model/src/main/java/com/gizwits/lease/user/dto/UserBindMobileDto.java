package com.gizwits.lease.user.dto;

import java.io.Serializable;

/**
 * Description:
 * User: yinhui
 * Date: 2018-03-02
 */
public class UserBindMobileDto implements Serializable{

    private String mobile;

    private Integer code;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
