package com.gizwits.lease.config.bean;

import com.gizwits.lease.device.entity.dto.ControlDto;
import org.springframework.context.ApplicationEvent;

/**
 * @author Jcxcc
 * @date 2018/9/28
 * @since 1.0
 */
public class SnotiDeviceControlEvent extends ApplicationEvent {

    private ControlDto controlDto;

    public SnotiDeviceControlEvent(ControlDto source) {
        super(source);
        this.controlDto = source;
    }

    public ControlDto getSnotiDeviceControlDTO() {
        return controlDto;
    }
}
