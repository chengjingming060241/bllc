package com.gizwits.boot.dto;

import java.io.Serializable;

public class JwtAuthenticationDto implements Serializable {
    private static final long serialVersionUID = 1250166508152483573L;

    private final String token;


    public JwtAuthenticationDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }
}
