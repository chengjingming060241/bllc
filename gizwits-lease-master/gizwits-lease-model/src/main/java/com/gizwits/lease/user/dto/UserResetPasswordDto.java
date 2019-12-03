package com.gizwits.lease.user.dto;

import com.gizwits.boot.validators.Mobile;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Description:
 * User: yinhui
 * Date: 2017-11-14
 */
public class UserResetPasswordDto implements Serializable{

    /**
     * 手机号
     */
    @NotBlank
    @Mobile
    private String mobile;
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
    /**图形验证码*/

    private String pictureCode;
    /**图形验证码id*/

    private String pictureId;

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

    public String getPictureCode() {
        return pictureCode;
    }

    public void setPictureCode(String pictureCode) {
        this.pictureCode = pictureCode;
    }

    public String getPictureId() { return pictureId; }

    public void setPictureId(String pictureId) { this.pictureId = pictureId; }
}
