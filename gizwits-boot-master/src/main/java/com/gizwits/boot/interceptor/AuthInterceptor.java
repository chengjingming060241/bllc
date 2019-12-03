package com.gizwits.boot.interceptor;

import com.gizwits.boot.base.Constants;
import com.gizwits.boot.config.RedisConfigService;
import com.gizwits.boot.exceptions.SysExceptionEnum;
import com.gizwits.boot.exceptions.SystemException;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.ParamUtil;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private SimpleCacheManager cacheManager;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private RedisConfigService redisConfigService;


    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String requestURI = httpServletRequest.getServletPath();
        String accessToken = httpServletRequest.getHeader(Constants.TOKEN_HEADER_NAME);
        if (ParamUtil.isNullOrEmptyOrZero(accessToken)) {
            throw new SystemException(SysExceptionEnum.NO_LOGIN.getCode(), SysExceptionEnum.NO_LOGIN.getMessage());
        }
        Cache cache = cacheManager.getCache(Constants.ACCESS_TOKEN_CACHE_NAME);
        if (ParamUtil.isNullOrEmptyOrZero(cache)) {
            throw new SystemException(SysExceptionEnum.NO_LOGIN.getCode(), SysExceptionEnum.NO_LOGIN.getMessage());
        }
        SysUser sysUser = cache.get(accessToken, SysUser.class);
        if (ParamUtil.isNullOrEmptyOrZero(sysUser)) {
            throw new SystemException(SysExceptionEnum.NO_LOGIN.getCode(), SysExceptionEnum.NO_LOGIN.getMessage());
        }
        Integer sysUserId = sysUser.getId();
        if (Objects.isNull(sysUserService.selectById(sysUserId))) {
            throw new SystemException(SysExceptionEnum.NO_LOGIN.getCode(), SysExceptionEnum.NO_LOGIN.getMessage());
        }

        Map<String, String> permissionMap = sysUserService.getPermissionList(sysUserId);
        String uri = httpServletRequest.getRequestURI();
        if (ParamUtil.isNullOrEmptyOrZero(permissionMap.get(uri))) {
            throw new SystemException(SysExceptionEnum.NO_PRIVILEGE.getCode(), SysExceptionEnum.NO_PRIVILEGE.getMessage() + ",uri=" + uri);
        }
//        //判断同一个人对同一个url不能请求太频繁
//        String cacheUrl = redisConfigService.getUrlBySysUserId(sysUserId);
//        if (StringUtils.isNotBlank(cacheUrl) && cacheUrl.equals(uri)) {
//            throw new SystemException(SysExceptionEnum.TOO_FREQUENT_OPERATION.getCode(), SysExceptionEnum.TOO_FREQUENT_OPERATION.getMessage());
//        } else {
//            redisConfigService.cacheSysUserWithUrl(sysUserId, uri);
//        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
