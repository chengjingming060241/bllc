package com.gizwits.boot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * Configuration - 通用配置
 *
 * @author lilh
 * @date 2017/7/20 16:56
 */
@Configuration
@EnableAsync
public class CommonConfiguration {

    /**
     * 线程池
     */
    @Bean
    @ConfigurationProperties(prefix = "gizwits.boot.task")
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        poolTaskExecutor.setCorePoolSize(5);
        poolTaskExecutor.setQueueCapacity(5);
        poolTaskExecutor.setMaxPoolSize(1000);
        poolTaskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        return poolTaskExecutor;
    }
}
