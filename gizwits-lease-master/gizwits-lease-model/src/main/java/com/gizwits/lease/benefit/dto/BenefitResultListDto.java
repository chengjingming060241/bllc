package com.gizwits.lease.benefit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gizwits.boot.base.Constants;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Description:
 * User: yinhui
 * Date: 2018-07-04
 */
public class BenefitResultListDto implements Serializable{
    /**
     * 订单号
     */
    @ApiModelProperty("订单号")
    private String orderNo;

    /**
     * 创建时间
     */
    @ApiModelProperty("执行分润时间")
    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN,timezone = "GMT+8")
    private Date utime;

    @ApiModelProperty("支付方式")
    private Integer payType;

    @ApiModelProperty("支付方式描述")
    private String payTypeDesc;

    /**
     * 所属人姓名
     */
    @ApiModelProperty("分润对象")
    private String personal;

    @ApiModelProperty("分润结果")
    private String shareBenefitResult;

    @ApiModelProperty("分润金额")
    private BigDecimal shareMoney;

    @JsonIgnore
    private Integer sysAccountId;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getUtime() {
        return utime;
    }

    public void setUtime(Date utime) {
        this.utime = utime;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getPayTypeDesc() {
        return payTypeDesc;
    }

    public void setPayTypeDesc(String payTypeDesc) {
        this.payTypeDesc = payTypeDesc;
    }

    public String getPersonal() {
        return personal;
    }

    public void setPersonal(String personal) {
        this.personal = personal;
    }

    public String getShareBenefitResult() {
        return shareBenefitResult;
    }

    public void setShareBenefitResult(String shareBenefitResult) {
        this.shareBenefitResult = shareBenefitResult;
    }

    public BigDecimal getShareMoney() {
        return shareMoney;
    }

    public void setShareMoney(BigDecimal shareMoney) {
        this.shareMoney = shareMoney;
    }

    public Integer getSysAccountId() {
        return sysAccountId;
    }

    public void setSysAccountId(Integer sysAccountId) {
        this.sysAccountId = sysAccountId;
    }
}
