package com.gizwits.lease.order.dto;

import javax.validation.constraints.NotNull;

/**
 * Created by zhl on 2017/9/12.
 */
public class AppUsingOrderDto {
    @NotNull
    private String sno;
    // @NotNull
    // private String openid;

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    // public String getOpenid() {
    //     return openid;
    // }
    //
    // public void setOpenid(String openid) {
    //     this.openid = openid;
    // }
}
