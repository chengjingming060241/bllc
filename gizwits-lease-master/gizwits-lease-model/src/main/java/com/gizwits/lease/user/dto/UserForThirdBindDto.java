package com.gizwits.lease.user.dto;

import com.gizwits.boot.validators.Mobile;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 第三方登录注册Dto
 * Created by yinhui on 2017/8/11.
 */
public class UserForThirdBindDto implements Serializable {
    /**
     *  手机号
     */
    @Mobile
    private String mobile;
    /**
     * 短线验证码
     */
    private String message;
    @NotNull
    private Integer thirdType;
    @NotBlank
    private String openId;
    @NotBlank
    private String token;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getThirdType() {
        return thirdType;
    }

    public void setThirdType(Integer thirdType) {
        this.thirdType = thirdType;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
