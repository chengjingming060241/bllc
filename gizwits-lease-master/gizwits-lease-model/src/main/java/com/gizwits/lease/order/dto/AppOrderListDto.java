package com.gizwits.lease.order.dto;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 订单使用中列表dto
 */
public class AppOrderListDto implements Serializable{
    @NotNull
    private String openId;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
