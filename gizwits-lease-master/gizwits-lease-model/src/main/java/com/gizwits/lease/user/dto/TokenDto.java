package com.gizwits.lease.user.dto;

import com.gizwits.boot.dto.JwtAuthenticationDto;

/**
 * Description:
 * User: yinhui
 * Date: 2018-02-28
 */
public class TokenDto {

    private JwtAuthenticationDto jwtAuthenticationDto;

    private String userToken;

    private String uid;

    public JwtAuthenticationDto getJwtAuthenticationDto() {
        return jwtAuthenticationDto;
    }

    public void setJwtAuthenticationDto(JwtAuthenticationDto jwtAuthenticationDto) {
        this.jwtAuthenticationDto = jwtAuthenticationDto;
    }

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}

