package com.gizwits.boot.event;

import java.util.List;

import org.springframework.context.ApplicationEvent;

/**
 * Event - 删除系统用户事件
 *
 * @author lilh
 * @date 2017/7/31 12:04
 */
public class SysUserDeteledEvent extends ApplicationEvent {
    private static final long serialVersionUID = -6741291696775980692L;

    public SysUserDeteledEvent(Object source) {
        super(source);
    }

    @SuppressWarnings("unchecked")
    public List<Integer> getDeletedUserIds() {
        return (List<Integer>) getSource();
    }
}
