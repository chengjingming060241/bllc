package com.gizwits.lease.user.dto;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

/**
 * Created by zhl on 2017/9/8.
 */
public class UserForUpdateMobileDto {

    private String oldMobile;

    private String password;

    @NotNull
    private String newMobile;
    @NotNull
    private String newCode;
    /**图形验证码*/

    private String pictureCode;
    /**图形验证码id*/

    private String pictureId;

    public String getNewMobile() {
        return newMobile;
    }

    public void setNewMobile(String newMobile) {
        this.newMobile = newMobile;
    }

    public String getNewCode() {
        return newCode;
    }

    public void setNewCode(String newCode) {
        this.newCode = newCode;
    }

    public String getOldMobile() {
        return oldMobile;
    }

    public void setOldMobile(String oldMobile) {
        this.oldMobile = oldMobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
