package com.gizwits.boot.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class ApplicationListenerReady implements ApplicationListener<ApplicationReadyEvent> {

    private final static Logger logger = LoggerFactory.getLogger(ApplicationListenerReady.class);

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        logger.info("           Gizwits Boot Application Start Successfully !               ");
    }
}