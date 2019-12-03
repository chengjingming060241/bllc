package com.gizwits.boot.dto;

import com.gizwits.boot.sys.entity.SysUser;

/**
 * @author lilh
 * @date 2017/7/31 17:02
 */
public class SysUserForPullDto {

    private Integer id;

    private String username;


    public SysUserForPullDto(SysUser user) {
        this.id = user.getId();
        this.username = user.getUsername();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
