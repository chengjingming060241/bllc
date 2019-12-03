package com.gizwits.boot.utils;

import javax.servlet.http.HttpServletRequest;

import com.gizwits.boot.common.WebCommonConfig;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Utils - web工具类
 *
 * @author lilh
 * @date 2017/7/6 13:14
 */
public final class WebUtils {

    private WebUtils() {
    }

    public static String getHeader(String key) {
        return getHttpServletRequest().getHeader(key);
    }

    public static String getRemoteAddr(HttpServletRequest request) {
        WebCommonConfig setting = SysConfigUtils.get(WebCommonConfig.class);
        String ip;
        if (Boolean.valueOf(setting.getIsUsedReverseProxy())) {
            ip = request.getHeader("x-forwarded-for");
            if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (StringUtils.isNotBlank(ip) && ip.indexOf(',') > 0) {
                ip = ip.substring(0, ip.indexOf(','));
            }
            if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
        } else {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String getRemoteAddr() {
        return getRemoteAddr(getHttpServletRequest());
    }

    private static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }
}
