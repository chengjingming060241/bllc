package com.gizwits.lease.refund.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 退款基础信息
 * </p>
 *
 * @author yqli
 * @since 2017-10-26
 */
@TableName("refund_base")
public class RefundBase extends Model<RefundBase> {

    private static final long serialVersionUID = 1L;

    @TableId("refund_no")
    private String refundNo;
    private BigDecimal amount;
    private Date ctime;
    @TableField("order_no")
    private String orderNo;
    @TableField("trade_no")
    private String tradeNo;
    /**
     * 123
     */
    @TableField("refund_method")
    private Integer refundMethod;


    public String getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Integer getRefundMethod() {
        return refundMethod;
    }

    public void setRefundMethod(Integer refundMethod) {
        this.refundMethod = refundMethod;
    }

    @Override
    protected Serializable pkVal() {
        return this.refundNo;
    }

}
