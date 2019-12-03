package com.gizwits.boot.config;

import com.gizwits.boot.interceptor.AuthInterceptor;
import com.gizwits.boot.utils.ParamUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {

    private final static Logger logger = LoggerFactory.getLogger(InterceptorConfig.class);

    @Value("${gizwits.boot.authUrl}")
    private  String  authUrl;

    @Bean
    AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }


    public void addInterceptors(InterceptorRegistry registry) {

        List<String> authUrlList = new ArrayList<>();
        //权限资源
        authUrlList.add( "/sys/sysUser/login");
        authUrlList.add("/sys/sysUser/resetPwd");
        authUrlList.add("/sys/sysUser/getListBymobile");
        if(!ParamUtil.isNullOrEmptyOrZero(authUrl)){
            String[] auths=authUrl.split(",");
            for(int i=0;i<auths.length;i++){
                authUrlList.add(auths[i]);
            }
        }

        //静态资源
        authUrlList.add("/*");
        authUrlList.add("/*.html");
        authUrlList.add("/favicon.ico");
        authUrlList.add("/**/*.html");
        authUrlList.add("/**/*.css");
        authUrlList.add("/**/*.js");

        //Swagger资源
        authUrlList.add("/webjars/**");
        authUrlList.add("/swagger-resources/**");
        authUrlList.add("/v2/api-docs");

        String[] strings = new String[authUrlList.size()];
        authUrlList.toArray(strings);

        logger.info("授权的url是:"+authUrl);
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns(strings);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}