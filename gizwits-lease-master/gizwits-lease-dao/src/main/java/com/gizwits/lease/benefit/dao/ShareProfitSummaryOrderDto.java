package com.gizwits.lease.benefit.dao;

import java.math.BigDecimal;

public class ShareProfitSummaryOrderDto {

    private Integer orderCount;

    private BigDecimal orderMoney;

    public Integer getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(Integer orderCount) {
        this.orderCount = orderCount;
    }

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }
}