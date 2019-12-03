package com.gizwits.boot.dto;

import java.io.Serializable;

/**
 * 权限树
 * Created by Chloe on 2017/7/5.
 */
public class PermissionTreeDto implements Serializable{
    private String token;
    private Integer permissionId;

    public String getToken() {return token;}

    public void setToken(String username) {this.token = token;}

    public Integer getPermissionId() {return permissionId;}

    public void setPermissionId(Integer permissionId) {this.permissionId = permissionId;}
}
