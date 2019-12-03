package com.gizwits.lease.wallet.dto;

/**
 * Created by GaGi on 2017/8/4.
 */

import javax.validation.constraints.NotNull;

/**
 * 用户交易dto
 */
public class WalletPayDto {

    @NotNull
    private String orderNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}
