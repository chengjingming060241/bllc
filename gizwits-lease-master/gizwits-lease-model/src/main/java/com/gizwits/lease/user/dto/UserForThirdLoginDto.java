package com.gizwits.lease.user.dto;

import com.gizwits.boot.validators.Mobile;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * 第三方登录注册Dto
 * Created by yinhui on 2017/8/11.
 */
public class UserForThirdLoginDto implements Serializable {

    @NotNull
    private Integer thirdType;
    @NotBlank
    private String openId;

    @ApiModelProperty("产品的appId")
    private String appId;

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

    public String getAppId() { return appId; }

    public void setAppId(String appId) { this.appId = appId; }
}
