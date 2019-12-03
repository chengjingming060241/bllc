package com.gizwits.lease.intercepter;

import com.gizwits.boot.base.Constants;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AppCorsIntercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o)
            throws Exception {
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");// 允许跨域
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");// 允许携带Cookie
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE");// 允许请求的方法
        httpServletResponse.setHeader("Access-Control-Allow-Headers",
                "Accept,Origin,No-Cache,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Version," +
                        Constants.TOKEN_HEADER_NAME);// 允许自定义的头部
        httpServletResponse.setHeader("Access-Control-Max-Age", "3600");// 缓存预检请求的秒数
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o,
                           ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {

    }
}
