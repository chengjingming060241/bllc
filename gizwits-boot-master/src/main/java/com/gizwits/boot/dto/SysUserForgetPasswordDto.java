package com.gizwits.boot.dto;

import com.gizwits.boot.validators.Mobile;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

import javax.validation.constraints.Pattern;

/**
 * 忘记密码dto
 * Created by yinhui on 2017/8/8.
 */
public class SysUserForgetPasswordDto implements Serializable {
    /**
     * 手机号
     */
    @NotBlank
    @Mobile
    private String mobile;

    private Integer userId;

    @Email
    private String email;
    /**
     * 短信验证吗
     */
    private String message;
    /**
     * 新密码
     */
    @NotBlank
    @Pattern(regexp = "^[0-9a-zA-Z\\-_%`~!@#\\u0024\\u005E&\\u002A\\u0028\\u0029\\u002B=]+$", message = "参数格式错误")
    @Length(min = 6, max = 18)
    private String newPassword;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Integer getUserId() { return userId; }

    public void setUserId(Integer userId) { this.userId = userId; }
}
