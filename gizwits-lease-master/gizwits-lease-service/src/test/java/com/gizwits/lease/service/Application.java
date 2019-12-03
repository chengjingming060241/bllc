package com.gizwits.lease.service;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author yuqing
 * @date 2018-02-05
 */
@SpringBootApplication
@ComponentScan("com.gizwits")
@MapperScan(basePackages = {"com.gizwits.lease.*.dao"})
public class Application {

    public static void main(String args[]) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

}
