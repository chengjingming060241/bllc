package com.gizwits.lease.wallet.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gizwits.boot.annotation.Query;

/**
 * 充值单查询dto
 * Created by yinhui on 2017/8/9.
 */
public class UserWalletChargeOrderQueryDto implements Serializable {
    /**
     * 用户详情内的充值列表
     */
    @Query(field = "user_id")
    private Integer userId;
    /**
     * 充值单号
     */
    @Query(field = "charge_order_no", operator = Query.Operator.like)
    private String chargeNo;
    /**
     * 用户昵称
     */
    @Query(field = "username",operator = Query.Operator.like)
    private String nickName;
    /**
     * 开始时间
     */
    @Query(field = "pay_time", operator = Query.Operator.ge)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date begin;

    @Query(field = "pay_time", operator = Query.Operator.le)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date end;

    /**
     * 用户名
     */
    @JsonFormat
    @Query(field = "user_id",operator = Query.Operator.in)
    private List<Integer> userIds;

    @JsonIgnore
    @Query(field = "charge_order_no", operator = Query.Operator.in)
    private List<String> ids;

    public Integer getUserId() { return userId; }

    public void setUserId(Integer userId) { this.userId = userId; }

    public List<Integer> getUserIds() { return userIds; }

    public void setUserIds(List<Integer> userIds) { this.userIds = userIds; }

    public String getChargeNo() {
        return chargeNo;
    }

    public void setChargeNo(String chargeNo) {
        this.chargeNo = chargeNo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Date getBegin() {return begin;}

    public void setBegin(Date begin) {this.begin = begin;}

    public Date getEnd() {return end;}

    public void setEnd(Date end) {this.end = end;}

    public List<String> getIds() {
        return ids;
    }

    public void setIds(List<String> ids) {
        this.ids = ids;
    }
}
