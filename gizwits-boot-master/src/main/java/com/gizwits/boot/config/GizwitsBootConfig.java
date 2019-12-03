package com.gizwits.boot.config;

import com.google.common.cache.CacheBuilder;

import com.gizwits.boot.base.Constants;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by rongmc on 2017/6/19.
 */
@Component
@Configuration
@ConfigurationProperties(prefix="gizwits.boot")
public class GizwitsBootConfig {

    private String appKey ;

    private  String tokenHeader;

    private Integer accessTokenExpiration = 30;

    private String authUrl;

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }


    public Integer getAccessTokenExpiration() {
        return accessTokenExpiration;
    }

    public void setAccessTokenExpiration(Integer accessTokenExpiration) {
        this.accessTokenExpiration = accessTokenExpiration;
    }


    public String getTokenHeader() {
        return tokenHeader;
    }

    public void setTokenHeader(String tokenHeader) {
        this.tokenHeader = tokenHeader;
    }


    public String getAuthUrl() {
        return authUrl;
    }

    public void setAuthUrl(String authUrl) {
        this.authUrl = authUrl;
    }
}
