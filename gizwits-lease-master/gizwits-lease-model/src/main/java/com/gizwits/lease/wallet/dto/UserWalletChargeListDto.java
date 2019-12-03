package com.gizwits.lease.wallet.dto;

import com.baomidou.mybatisplus.annotations.TableField;

import java.io.Serializable;
import java.util.Date;

/**
 * 充值单列表dto
 * Created by yinhui on 2017/8/9.
 */
public class UserWalletChargeListDto implements Serializable {
    /**
     * 充值单号
     */
    private String chargeNo;
    /**
     * 用户昵称
     */
    private String nickName;

    private Integer userId;
    /**
     * 充值金额
     */
    private Double fee;
    /**
     * 充值时间
     */
    private Date payTime;
    /**
     * 充值方式
     */
    private String payType;
    /**
     * 充值状态
     */
    private String status;


    /**
     * 是否为代充卡
     */
    private boolean isConcession;

    /**
     * 年卡:YEAR,体验卡:TRY
     */
    private String cardType;

    /**
     * 套餐使用次数
     */
    private Integer usableTimes;

    /**
     * 套餐默认赠送次数
     */
    private Integer defaultUsage;

    public boolean isConcession() {
        return isConcession;
    }

    public void setConcession(boolean concession) {
        isConcession = concession;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Integer getUsableTimes() {
        return usableTimes;
    }

    public void setUsableTimes(Integer usableTimes) {
        this.usableTimes = usableTimes;
    }

    public Integer getDefaultUsage() {
        return defaultUsage;
    }

    public void setDefaultUsage(Integer defaultUsage) {
        this.defaultUsage = defaultUsage;
    }

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

    public Integer getUserId() { return userId; }

    public void setUserId(Integer userId) { this.userId = userId; }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
