package com.gizwits.lease.order.dto;

import java.math.BigDecimal;

public class ServiceOrderAmountDto {

    private String orderNo;

    private BigDecimal amount;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}