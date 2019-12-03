package com.gizwits.boot.dto;

import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.security.Principal;

/**
 * Created by rongmc on 2017/6/27.
 */
public class LoginDto implements Serializable {

    @NotBlank(message ="请输入用户名!")
    private String username ;
    @NotBlank(message ="请输入密码!")
    private String password ;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
