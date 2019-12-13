package com.gizwits.lease.init;

import com.gizwits.boot.config.InterceptorConfig;
import com.gizwits.lease.intercepter.AppCorsIntercepter;
import com.gizwits.lease.intercepter.AppUserAuthIntercepter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhl on 2017/10/30.
 */
@Configuration
public class LeaseIntercepterConfig  extends WebMvcConfigurerAdapter{

    @Bean
    AppUserAuthIntercepter getAppUserAuthIntercepter(){
        return new AppUserAuthIntercepter();
    }

    @Bean
    AppCorsIntercepter getAppCorsInterceper(){
        return new AppCorsIntercepter();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> whiteAuthUri = new ArrayList<>();
        whiteAuthUri.add("/app/user/login");
        whiteAuthUri.add("/app/user/register");
        whiteAuthUri.add("/app/user/message");
        whiteAuthUri.add("/app/user/messageCode");
        whiteAuthUri.add("/app/user/thirdRegister");
        whiteAuthUri.add("/app/user/thirdLogin");
        whiteAuthUri.add("/app/user/picture");
        whiteAuthUri.add("/app/user/forgetPassword");
        whiteAuthUri.add("/app/user/messageCodeForAdmin");
        whiteAuthUri.add("/app/user/forgetPassword");
	    whiteAuthUri.add("/app/user/bindUser");
        whiteAuthUri.add("/app/wx/init");
        whiteAuthUri.add("/app/aliPay/verify");
        whiteAuthUri.add("/app/aliPay/notify");
        whiteAuthUri.add("/app/aliPay/userinfo");
        whiteAuthUri.add("/app/wx/weixin");
        whiteAuthUri.add("/app/wxPay/wxPayNotify");
        whiteAuthUri.add("/app/scan/search");
        // whiteAuthUri.add("/app/manage/messageAuthenticationCode");
        // whiteAuthUri.add("/app/manage/forgetPassword");
        // whiteAuthUri.add("/app/manage/resetPwd");
        // whiteAuthUri.add("/app/manage/login");
        whiteAuthUri.add("/app/wxPay/prePay");
        whiteAuthUri.add("/app/aliPay/sign");
        whiteAuthUri.add("/app/aliPay/pay");
        whiteAuthUri.add("/app/user/bindMessageCode");
        whiteAuthUri.add("/app/user/messageCodeForBindWx");
        whiteAuthUri.add("/app/user/judge");
        whiteAuthUri.add("/app/user/thirdBindByMobile");
        whiteAuthUri.add("/app/user/messageCodeBind");
        whiteAuthUri.add("/app/chinaArea/area");
        whiteAuthUri.add("/app/manage/user/login");
        whiteAuthUri.add("/app/manage/user/messageAuthenticationCode");
        whiteAuthUri.add("/app/manage/user/forgetPwd");
        whiteAuthUri.add("/app/message/advertisementDisplay/detail");
        whiteAuthUri.add("/app/user/sendCode");
        String[] strings = new String[whiteAuthUri.size()];
        whiteAuthUri.toArray(strings);

        // 解决跨域
        registry.addInterceptor(getAppCorsInterceper())
                .addPathPatterns("/app/**");

        registry.addInterceptor(getAppUserAuthIntercepter())
                .addPathPatterns("/app/**")
                .excludePathPatterns(strings);
    }
}
