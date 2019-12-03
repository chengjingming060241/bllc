package com.gizwits.lease.util;

import com.gizwits.lease.redis.RedisService;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component
public class KaptchaConfig {

    @Autowired
    private RedisService redisService;

    @Bean
    public DefaultKaptcha getDefaultKaptcha(){

        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.border", "yes");
        properties.setProperty("kaptcha.border.color", "105,179,90");
        properties.setProperty("kaptcha.textproducer.font.color", "blue");
        properties.setProperty("kaptcha.image.width", "240");
        properties.setProperty("kaptcha.image.height", "80");
        properties.setProperty("kaptcha.textproducer.font.size", "60");
        properties.setProperty("kaptcha.session.key", "code");
        properties.setProperty("kaptcha.textproducer.char.length", "4");
//        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");
        properties.setProperty("kaptcha.obscurificator.impl", "com.gizwits.lease.util.NoDistortion");
        com.google.code.kaptcha.util.Config config = new com.google.code.kaptcha.util.Config(properties);
        defaultKaptcha.setConfig(config);

        return defaultKaptcha;
    }
}