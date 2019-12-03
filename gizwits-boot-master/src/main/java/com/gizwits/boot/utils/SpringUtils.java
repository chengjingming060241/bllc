package com.gizwits.boot.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Utils - Spring
 *
 * @author lilh
 * @date 2017/7/3 17:55
 */
@Lazy(false)
@Component
public final class SpringUtils implements ApplicationContextAware, DisposableBean {

    private static ApplicationContext applicationContext;

    private SpringUtils() {
    }

    /**
     * 获取applicationContext
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取实例
     *
     * @param name bean名称
     * @return 实例
     */
    public static Object getBean(String name) {
        Assert.hasText(name);
        return applicationContext.getBean(name);
    }

    /**
     * 获取bean实例
     *
     * @param type bean类型
     * @param <T>  类型
     * @return 实例
     */
    public static <T> T getBean(Class<T> type) {
        Assert.notNull(type);
        return applicationContext.getBean(type);
    }

    /**
     * 获取实例
     *
     * @param name 名称
     * @param type 类型
     * @param <T>  类型
     * @return 实例
     */
    public static <T> T getBean(String name, Class<T> type) {
        Assert.hasText(name);
        Assert.notNull(type);
        return applicationContext.getBean(name, type);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    @Override
    public void destroy() throws Exception {
        SpringUtils.applicationContext = null;
    }
}
