package com.gizwits.lease.wallet.dto;

import java.io.Serializable;

/**
 * Created by yinhui on 2017/8/17.
 */
public class RechargeMoneyDto implements Serializable {
    /***
     * id
     */
    private Integer id;
    /**
     * 优惠金额
     */
    private Double discountMoney;
    /**
     * 充值金额
     */
    private Double rechargeMoney;

    /**
     * 排序优先级
     */
    private Integer sort;

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public Double getDiscountMoney() {return discountMoney;}

    public void setDiscountMoney(Double discountMoney) {this.discountMoney = discountMoney;}

    public Double getRechargeMoney() {return rechargeMoney;}

    public void setRechargeMoney(Double rechargeMoney) {this.rechargeMoney = rechargeMoney;}

    public Integer getSort() {return sort;}

    public void setSort(Integer sort) {this.sort = sort;}
}
