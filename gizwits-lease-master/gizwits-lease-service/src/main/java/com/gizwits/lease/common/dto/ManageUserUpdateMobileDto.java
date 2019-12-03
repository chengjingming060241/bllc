package com.gizwits.lease.common.dto;

import com.gizwits.boot.validators.Mobile;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

public class ManageUserUpdateMobileDto {
    @ApiModelProperty("手机号")
    @NotNull(message = "mobile may not be null")
    private String mobile;

    @ApiModelProperty("密码")
    @NotNull(message = "password may not be null")
    private String password;

    @ApiModelProperty("验证码")
    @NotNull(message = "code may not be null")
    private String code;

    @ApiModelProperty("新手机号")
    @NotNull(message = "updateMobile may not be null")
    @Mobile
    private String updateMobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getUpdateMobile() {
        return updateMobile;
    }

    public void setUpdateMobile(String updateMobile) {
        this.updateMobile = updateMobile;
    }
}
