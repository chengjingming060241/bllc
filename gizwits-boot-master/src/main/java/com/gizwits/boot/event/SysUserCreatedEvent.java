package com.gizwits.boot.event;

import java.util.ArrayList;
import java.util.List;

import com.gizwits.boot.sys.entity.SysUser;
import org.springframework.context.ApplicationEvent;

/**
 * Event - 创建账号事件
 *
 * @author lilh
 * @date 2017/7/26 19:30
 */
public class SysUserCreatedEvent extends ApplicationEvent {
    private static final long serialVersionUID = 5919298581844810632L;

    private List<Integer> roleIds = new ArrayList<>();

    public SysUserCreatedEvent(Object source, List<Integer> roleIds) {
        super(source);
        this.roleIds = roleIds;
    }

    public SysUser getSysUser() {
        return (SysUser) getSource();
    }

    public List<Integer> getRoleIds() {
        return roleIds;
    }
}
