package com.gizwits.lease.benefit.dto;

import java.math.BigDecimal;

public class OrderShareProfitSumDto {

    private BigDecimal orderMoney;

    private BigDecimal shareMoney;

    private Integer orderNum;

    public BigDecimal getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(BigDecimal orderMoney) {
        this.orderMoney = orderMoney;
    }

    public BigDecimal getShareMoney() {
        return shareMoney;
    }

    public void setShareMoney(BigDecimal shareMoney) {
        this.shareMoney = shareMoney;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }
}