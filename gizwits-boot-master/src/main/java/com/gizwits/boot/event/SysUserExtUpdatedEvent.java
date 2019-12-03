package com.gizwits.boot.event;

import com.gizwits.boot.sys.entity.SysUserExt;
import org.springframework.context.ApplicationEvent;

/**
 * Event - 系统帐号扩展信息更新
 *
 * @author lilh
 * @date 2017/8/16 16:41
 */
public class SysUserExtUpdatedEvent extends ApplicationEvent {

    private static final long serialVersionUID = -2013904834290792586L;

    public SysUserExtUpdatedEvent(Object source) {
        super(source);
    }

    public SysUserExt getExt() {
        return (SysUserExt) getSource();
    }
}
