package com.gizwits.boot.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Utils - 系统配置
 *
 * @author lilh
 * @date 2017/7/3 19:19
 */
public final class SysConfigUtils {

    private static Map<Class<?>, Object> configClasses = new HashMap<>();

    private static InvocationHandler handler;

    private SysConfigUtils() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Class<T> configClass) {
        T sysConfig = (T) configClasses.get(configClass);
        if (Objects.isNull(sysConfig)) {
            initHandlerIfNecessary();
            sysConfig = (T) Proxy.newProxyInstance(SysConfigInvocationHandler.class.getClassLoader(), new Class[] {configClass}, handler);
            configClasses.put(configClass, sysConfig);
        }
        return sysConfig;
    }

    private static void initHandlerIfNecessary() {
        if (Objects.isNull(handler)) {
            handler = SpringUtils.getBean(SysConfigInvocationHandler.class);
        }
    }

}
