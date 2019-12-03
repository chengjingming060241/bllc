package com.gizwits.lease.device.entity.dto;

/**
 * 维护人员
 * Created by yinhui on 2017/7/14.
 */
public class DeviceMaintainerDto {
    private Integer id;
    private String name;
    private String avatar;
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}
}
