package com.gizwits.lease.order.dto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.Objects;
import javax.validation.constraints.NotNull;

/**
 * Created by GaGi on 2017/7/31.
 */
public class ChargeOrderDto {

    /**
     * 金额
     */
    @JsonIgnore
    private Double fee;


    /***
     * 充值金额id
     */
    private Integer rechargeId;


    /**
     * 年卡:YEAR,体验卡:TRY
     */
    private String cardType;

    /**
     * 用户ID
     */
    private String openid;

    /**
     * 充值方式  充值分为全额代充 : full、让利代充 :let
     */
    private String RechargeMode;

    private String  cardId;


    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getRechargeMode() {
        return RechargeMode;
    }

    public void setRechargeMode(String rechargeMode) {
        RechargeMode = rechargeMode;
    }

    public Double getFee() {
        return fee;
    }

    public void setFee(Double fee) {
        this.fee = fee;
    }

    public Integer getRechargeId() {
        return rechargeId;
    }

    public void setRechargeId(Integer rechargeId) {
        this.rechargeId = rechargeId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}

