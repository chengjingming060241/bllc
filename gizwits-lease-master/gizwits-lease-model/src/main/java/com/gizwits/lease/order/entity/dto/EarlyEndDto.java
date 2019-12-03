package com.gizwits.lease.order.entity.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class EarlyEndDto {
    @ApiModelProperty(value = "订单号", required = true)
    private String orderNo;
    @ApiModelProperty(value = "app调用时必填")
    private String openid;
    @ApiModelProperty(value = "不需要填")
    private List<Integer> sysUsers;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public List<Integer> getSysUsers() {
        return sysUsers;
    }

    public void setSysUsers(List<Integer> sysUsers) {
        this.sysUsers = sysUsers;
    }
}
