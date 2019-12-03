package com.gizwits.lease.wallet.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * kali充值记录dto
 * Created by yinhui on 2017/8/21.
 */
public class KaliWalletUseDto implements Serializable {
    /**充值时间*/
    private Date rechargeTime;
    /**充值金额*/
    private BigDecimal fee;
    /**余额*/
    private BigDecimal balance;

    public Date getRechargeTime() {return rechargeTime;}

    public void setRechargeTime(Date rechargeTime) {this.rechargeTime = rechargeTime;}

    public BigDecimal getFee() {return fee;}

    public void setFee(BigDecimal fee) {this.fee = fee;}

    public BigDecimal getBalance() {return balance;}

    public void setBalance(BigDecimal balance) {this.balance = balance;}
}
