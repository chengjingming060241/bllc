package com.gizwits.lease.user.dto;

import com.gizwits.boot.validators.Mobile;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 第三方登录注册Dto
 * Created by yinhui on 2017/8/11.
 */
public class UserForThirdUnbindDto implements Serializable {
     @NotNull
    private Integer thirdType;

    public Integer getThirdType() {
        return thirdType;
    }

    public void setThirdType(Integer thirdType) {
        this.thirdType = thirdType;
    }
}
