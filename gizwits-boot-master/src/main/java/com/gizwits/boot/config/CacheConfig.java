package com.gizwits.boot.config;

import com.google.common.cache.CacheBuilder;

import com.gizwits.boot.base.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig  {

    @Autowired
    private GizwitsBootConfig gizwitsBootConfig;

    @Bean
    public SimpleCacheManager cacheManager() {
        SimpleCacheManager manager = new SimpleCacheManager();
        List list = new ArrayList();
        list.add(buildAccessTokenCache());
        manager.setCaches(list);

        return manager;
    }

    private GuavaCache buildAccessTokenCache() {
        return new GuavaCache(Constants.ACCESS_TOKEN_CACHE_NAME,
                CacheBuilder.newBuilder()
                        .recordStats()
                        .expireAfterAccess(gizwitsBootConfig.getAccessTokenExpiration(), TimeUnit.MINUTES)
                        //.expireAfterWrite(accessTokenExpiration, TimeUnit.MINUTES)
                        .build());
    }

}