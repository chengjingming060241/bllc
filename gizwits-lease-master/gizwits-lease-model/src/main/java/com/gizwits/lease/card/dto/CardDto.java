package com.gizwits.lease.card.dto;


import com.baomidou.mybatisplus.annotations.TableField;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class CardDto {

    @ApiModelProperty("卡券ID")
    private String cardId;

    @ApiModelProperty("卡券名称")
    private String title;

    @ApiModelProperty("卡券类型 CASH:代金券, DISCOUNT:折扣券,年卡:YEAR,体验卡:TRY")
    private String cardType;

    @ApiModelProperty("使用时间的类型 DATE_TYPE_FIX_TIME_RANGE:固定日期区间, DATE_TYPE_FIX_TERM:固定时长（自领取后按天算）")
    private String dateType;

    @ApiModelProperty("DATE_TYPE_FIX_TIME_RANGE时专用 ，表示起用时间。从1970年1月1日00:00:00至起用时间的秒数。（单位为秒）")
    private Date dateBeginTimestamp;

    @ApiModelProperty("DATE_TYPE_FIX_TIME_RANGE时专用 ，表示结束时间。（单位为秒）")
    private Date dateEndTimestamp;

    @ApiModelProperty("DATE_TYPE_FIX_TERM时专用 ，表示自领取后多少天内有效，领取后当天有效填写0。（单位为天）")
    private Integer dateFixedTerm;

    @ApiModelProperty("DATE_TYPE_FIX_TERM时专用 ，表示自领取后多少天开始生效。（单位为天）")
    private Integer dateFixedBeginTerm;

    @ApiModelProperty("卡券现有库存的数量")
    private Integer quantity;

    @ApiModelProperty("是否投放微信")
    private Integer dispatchWeb;

    @ApiModelProperty("是否投放APP")
    private Integer dispatchApp;

    @ApiModelProperty("套餐价格")
    private Integer cardCost;

    @ApiModelProperty("套餐可使用次数")
    private Integer usableTimes;

    @ApiModelProperty("套餐、年卡用户赠送的次数")
    private Integer defaultUsage;

    @ApiModelProperty("分润次数")
    private Integer shareCount;

    @ApiModelProperty("运营商为用户代充值次数")
    private Integer rechargeCount;

    @ApiModelProperty("运营商为用户代充值金额下限")
    private Integer rechargeCost;

    @ApiModelProperty("卡券适用产品ID, NULL为全部产品适用")
    private Integer productId;

    @ApiModelProperty("卡券适用运营商ID, NULL为全部运营商适用, 多个运营商ID使用,分隔")
    private String operatorIds;

    @ApiModelProperty("卡券适用运营商名称")
    private String operatorName;

    @ApiModelProperty("是否为让利代充卡")
    private Boolean concession;

    public Boolean getConcession() {
        return concession;
    }

    public void setConcession(Boolean concession) {
        this.concession = concession;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public Integer getRechargeCost() {
        return rechargeCost;
    }

    public void setRechargeCost(Integer rechargeCost) {
        this.rechargeCost = rechargeCost;
    }

    public String getOperatorIds() {
        return operatorIds;
    }

    public void setOperatorIds(String operatorIds) {
        this.operatorIds = operatorIds;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getCardCost() {
        return cardCost;
    }

    public void setCardCost(Integer cardCost) {
        this.cardCost = cardCost;
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

    public Integer getShareCount() {
        return shareCount;
    }

    public void setShareCount(Integer shareCount) {
        this.shareCount = shareCount;
    }

    public Integer getRechargeCount() {
        return rechargeCount;
    }

    public void setRechargeCount(Integer rechargeCount) {
        this.rechargeCount = rechargeCount;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getDateType() {
        return dateType;
    }

    public void setDateType(String dateType) {
        this.dateType = dateType;
    }

    public Date getDateBeginTimestamp() {
        return dateBeginTimestamp;
    }

    public void setDateBeginTimestamp(Date dateBeginTimestamp) {
        this.dateBeginTimestamp = dateBeginTimestamp;
    }

    public Date getDateEndTimestamp() {
        return dateEndTimestamp;
    }

    public void setDateEndTimestamp(Date dateEndTimestamp) {
        this.dateEndTimestamp = dateEndTimestamp;
    }

    public Integer getDateFixedTerm() {
        return dateFixedTerm;
    }

    public void setDateFixedTerm(Integer dateFixedTerm) {
        this.dateFixedTerm = dateFixedTerm;
    }

    public Integer getDateFixedBeginTerm() {
        return dateFixedBeginTerm;
    }

    public void setDateFixedBeginTerm(Integer dateFixedBeginTerm) {
        this.dateFixedBeginTerm = dateFixedBeginTerm;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getDispatchWeb() {
        return dispatchWeb;
    }

    public void setDispatchWeb(Integer dispatchWeb) {
        this.dispatchWeb = dispatchWeb;
    }

    public Integer getDispatchApp() {
        return dispatchApp;
    }

    public void setDispatchApp(Integer dispatchApp) {
        this.dispatchApp = dispatchApp;
    }
}
