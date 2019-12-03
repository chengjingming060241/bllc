package com.gizwits.boot.config;

import com.baomidou.mybatisplus.enums.DBType;
import com.baomidou.mybatisplus.plugins.PaginationInterceptor;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
*@Author rongmc
*@Date 2017/5/24 19:52
*MyBatisPlus配置
*/
@Configuration
@MapperScan(basePackages = "com.gizwits.boot.*.dao")
public class MybatisPlusConfig {

	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		paginationInterceptor.setDialectType(DBType.MYSQL.getDb());
		return paginationInterceptor;
	}



}
