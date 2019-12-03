package com.gizwits.lease.order.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Created by zhl on 2017/8/15.
 */
public class DepositOrderDto {

    private String sno;

     private BigDecimal money;

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
