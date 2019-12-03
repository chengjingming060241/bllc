package com.gizwits.lease.intercepter;

import com.gizwits.boot.base.Constants;
import com.gizwits.boot.enums.SysUserStatus;
import com.gizwits.boot.exceptions.SysExceptionEnum;
import com.gizwits.boot.exceptions.SystemException;
import com.gizwits.boot.sys.entity.SysUser;
import com.gizwits.boot.sys.service.SysUserService;
import com.gizwits.boot.utils.ParamUtil;
import com.gizwits.boot.utils.SysConfigUtils;
import com.gizwits.lease.config.CommonSystemConfig;
import com.gizwits.lease.redis.RedisService;
import com.gizwits.lease.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 * Created by zhl on 2017/10/30.
 */
@Component
public class AppUserAuthIntercepter implements HandlerInterceptor {

    @Autowired
    private RedisService redisService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private CacheManager cacheManager;

    /**
     * 这里兼容了用户端和管理端，所以需要根据token分别获取user和sysUser，
     * 另外，一般用户端和管理端的功能都是固定不变的，所以只要是app开头的请求，都不检查是否有权限，省去添加权限的麻烦
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String method = httpServletRequest.getMethod();
        if ("OPTIONS".equals(method)) {
            return true;
        }
        String uri = httpServletRequest.getRequestURI();
        String accessToken = httpServletRequest.getHeader(Constants.TOKEN_HEADER_NAME);
        if (ParamUtil.isNullOrEmptyOrZero(accessToken)) {
            throw new SystemException(SysExceptionEnum.NO_LOGIN.getCode(), SysExceptionEnum.NO_LOGIN.getMessage());
        }

        Cache cache = cacheManager.getCache(Constants.ACCESS_TOKEN_CACHE_NAME);
        if (ParamUtil.isNullOrEmptyOrZero(cache)) {
            throw new SystemException(SysExceptionEnum.NO_LOGIN.getCode(), SysExceptionEnum.NO_LOGIN.getMessage());
        }
        User user = redisService.getAppUser(accessToken);
        SysUser sysUser = cache.get(accessToken, SysUser.class);
        if (ParamUtil.isNullOrEmptyOrZero(sysUser) && ParamUtil.isNullOrEmptyOrZero(user)) {
            throw new SystemException(SysExceptionEnum.NO_LOGIN.getCode(), SysExceptionEnum.NO_LOGIN.getMessage());
        }
        if (!ParamUtil.isNullOrEmptyOrZero(sysUser)) {
            SysUser currentSysUser = sysUserService.selectById(sysUser.getId());
            if (Objects.isNull(currentSysUser)) {
                throw new SystemException(SysExceptionEnum.NO_LOGIN.getCode(), SysExceptionEnum.NO_LOGIN.getMessage());
            }
            if (Objects.equals(currentSysUser.getIsEnable(), SysUserStatus.DISABLE.getCode())) {
                cache.put(accessToken, null);
                throw new SystemException(SysExceptionEnum.USER_DISABLED.getCode(), SysExceptionEnum.USER_DISABLED.getMessage());
            }

            if (!uri.startsWith("/app/")) {
                Map<String, String> permissionMap = sysUserService.getPermissionList(sysUser.getId());
                if (ParamUtil.isNullOrEmptyOrZero(permissionMap.get(uri))) {
                    throw new SystemException(SysExceptionEnum.NO_PRIVILEGE.getCode(),
                            SysExceptionEnum.NO_PRIVILEGE.getMessage() + ",uri=" + uri);
                }
            }
        }
        if (!ParamUtil.isNullOrEmptyOrZero(user)) {
            if (!uri.startsWith("/app/")) {
                Map<String, String> permissionMap = sysUserService
                        .getRolePermissionList(SysConfigUtils.get(CommonSystemConfig.class).getAppUserRoleId());
                if (ParamUtil.isNullOrEmptyOrZero(permissionMap.get(uri))) {
                    throw new SystemException(SysExceptionEnum.NO_PRIVILEGE.getCode(),
                            SysExceptionEnum.NO_PRIVILEGE.getMessage() + ",uri=" + uri);
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
