package com.gizwits.lease.stat.vo;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

/**
 * Created by GaGi on 2017/7/19.
 */
public class StatOrderWidgetVo {

    @ApiModelProperty("新增订单数(昨日产生的订单数量)")
    private Integer orderCountYesterday;

    @ApiModelProperty("本月订单数(本月1号到昨天的订单数量)")
    private Integer orderCountMonth;

    @ApiModelProperty("订单完成数(所有已完成的订单数量)")
    private Integer orderFinishCount;

    @ApiModelProperty("累计订单总数")
    private Integer orderTotalCount;
    
    private Integer shareOrderCount;
    private BigDecimal shareOrderMoney;

    public StatOrderWidgetVo(Integer orderCountYesterday, Integer orderCountMonth, Integer orderFinishCount,
            Integer orderTotalCount) {
        this.orderCountYesterday = orderCountYesterday;
        this.orderCountMonth = orderCountMonth;
        this.orderFinishCount = orderFinishCount;
        this.orderTotalCount = orderTotalCount;
    }

    public Integer getOrderCountYesterday() {
        return orderCountYesterday;
    }

    public void setOrderCountYesterday(Integer orderCountYesterday) {
        this.orderCountYesterday = orderCountYesterday;
    }

    public Integer getOrderCountMonth() {
        return orderCountMonth;
    }

    public void setOrderCountMonth(Integer orderCountMonth) {
        this.orderCountMonth = orderCountMonth;
    }

    public Integer getOrderFinishCount() {
        return orderFinishCount;
    }

    public void setOrderFinishCount(Integer orderFinishCount) {
        this.orderFinishCount = orderFinishCount;
    }

    public Integer getOrderTotalCount() {
        return orderTotalCount;
    }

    public void setOrderTotalCount(Integer orderTotalCount) {
        this.orderTotalCount = orderTotalCount;
    }

    public Integer getShareOrderCount() {
        return shareOrderCount;
    }

    public void setShareOrderCount(Integer shareOrderCount) {
        this.shareOrderCount = shareOrderCount;
    }

    public BigDecimal getShareOrderMoney() {
        return shareOrderMoney;
    }

    public void setShareOrderMoney(BigDecimal shareOrderMoney) {
        this.shareOrderMoney = shareOrderMoney;
    }
}
