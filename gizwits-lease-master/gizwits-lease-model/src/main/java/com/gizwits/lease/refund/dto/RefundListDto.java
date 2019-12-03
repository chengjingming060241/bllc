package com.gizwits.lease.refund.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gizwits.boot.base.Constants;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Description:
 * User: yinhui
 * Date: 2018-07-04
 */
public class RefundListDto implements Serializable{

    @ApiModelProperty("退款单号")
    private String refundNo;
    @ApiModelProperty("退款状态码")
    private Integer status;
    @ApiModelProperty("退款状态描述")
    private String statusStr;
    @ApiModelProperty("订单号")
    private String orderNo;
    @ApiModelProperty("退款金额")
    private Double amount;
    @ApiModelProperty("用户昵称")
    private String nickname;
    @ApiModelProperty("用户手机号")
    private String userMobile;
    @ApiModelProperty("设备sno")
    private String sno;
    @ApiModelProperty("申请退款时间")
    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date ctime;
    @ApiModelProperty("申请退款审核时间")
    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
    private Date auditTime;

    public String getRefundNo() {
        return refundNo;
    }

    public void setRefundNo(String refundNo) {
        this.refundNo = refundNo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}
