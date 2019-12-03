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
public class UserForThirdRegisterDto implements Serializable {
    /**
     *  手机号
     */
    @Mobile
    private String mobile;
    /**
     * 密码
     */
    @Pattern(regexp = "^[0-9a-zA-Z\\-_%`~!@#\\u0024\\u005E&\\u002A\\u0028\\u0029\\u002B=]+$", message = "参数格式错误")
    @Length(min = 6, max = 18)
    private String password;
    /**
     * 短线验证码
     */
    private String message;
    @NotNull
    private Integer thirdType;
    @NotNull
    private String openId;

    private String token;

    private String appId;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

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

    public String getAppId() { return appId; }

    public void setAppId(String appId) { this.appId = appId; }
}
