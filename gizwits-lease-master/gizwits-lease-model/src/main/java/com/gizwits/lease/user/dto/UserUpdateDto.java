package com.gizwits.lease.user.dto;

import com.gizwits.boot.validators.Mobile;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by GaGi on 2017/8/18.
 */
public class UserUpdateDto {
    @NotBlank
    private String nickname;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
