package com.gizwits.lease.device.entity.dto;

import java.io.Serializable;

/**
 * Description:
 * User: yinhui
 * Date: 2018-02-28
 */
public class UserBindDeviceDto implements Serializable{


    private String mac;
    private Integer userId;

    public String getMac() { return mac; }

    public void setMac(String mac) { this.mac = mac; }


    public Integer getUserId() { return userId; }

    public void setUserId(Integer userId) { this.userId = userId; }
}
