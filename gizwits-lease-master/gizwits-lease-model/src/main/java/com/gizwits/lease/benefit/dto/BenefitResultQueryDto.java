package com.gizwits.lease.benefit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gizwits.boot.base.Constants;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Description:分润结果查询
 * User: yinhui
 * Date: 2018-07-04
 */
public class BenefitResultQueryDto implements Serializable{

    @ApiModelProperty("用户昵称")
    private String nickname;

    @ApiModelProperty("订单号")
    private String orderNo;

    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN,timezone = "GMT+8")
    private Date beginTime;

    @JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN,timezone = "GMT+8")
    private Date endTime;

   @JsonIgnore
    private int pageSize;

   @JsonIgnore
    private int current;

    @JsonIgnore
    private int begin;
    /**
     * 当前登录用户id
     */
    @JsonIgnore
    private List<String> orderNos;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Date getBeginTime() { return beginTime; }

    public void setBeginTime(Date beginTime) { this.beginTime = beginTime; }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public List<String> getOrderNos() { return orderNos; }

    public void setOrderNos(List<String> orderNos) { this.orderNos = orderNos; }
}
