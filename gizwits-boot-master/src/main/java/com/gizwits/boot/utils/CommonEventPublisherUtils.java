package com.gizwits.boot.utils;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * Utils - 事件发布
 *
 * @author lilh
 * @date 2017/7/5 11:08
 */
@Component
public class CommonEventPublisherUtils implements ApplicationEventPublisherAware, DisposableBean {


    private static ApplicationEventPublisher applicationEventPublisher;


    /**
     * 发布事件
     *
     * @param event 事件
     */
    public static void publishEvent(ApplicationEvent event) {
        publishEvent((Object) event);
    }

    private static void publishEvent(Object event) {
        Assert.notNull(event);
        applicationEventPublisher.publishEvent(event);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        CommonEventPublisherUtils.applicationEventPublisher = applicationEventPublisher;
    }

    @Override
    public void destroy() throws Exception {
        CommonEventPublisherUtils.applicationEventPublisher = null;
    }
}
