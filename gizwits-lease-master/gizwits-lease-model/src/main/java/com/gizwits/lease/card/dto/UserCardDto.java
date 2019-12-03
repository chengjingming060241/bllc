package com.gizwits.lease.card.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

public class UserCardDto {

    @ApiModelProperty("卡券ID")
    private String cardId;

    @ApiModelProperty("卡券名称")
    private String title;

    @ApiModelProperty("卡券类型 CASH:代金券, DISCOUNT:折扣券")
    private String cardType;

    @ApiModelProperty("使用时间的类型 DATE_TYPE_FIX_TIME_RANGE:固定日期区间, DATE_TYPE_FIX_TERM:固定时长（自领取后按天算）")
    private String dateType;

    @ApiModelProperty("DATE_TYPE_FIX_TIME_RANGE时专用 ，表示起用时间。")
    private Date dateBeginTimestamp;

    @ApiModelProperty("DATE_TYPE_FIX_TIME_RANGE时专用 ，表示结束时间。")
    private Date dateEndTimestamp;

    @ApiModelProperty("DATE_TYPE_FIX_TERM时专用 ，表示自领取后多少天内有效，领取后当天有效填写0。（单位为天）")
    private Integer dateFixedTerm;

    @ApiModelProperty("DATE_TYPE_FIX_TERM时专用 ，表示自领取后多少天开始生效。（单位为天）")
    private Integer dateFixedBeginTerm;

    @ApiModelProperty("代金券专用，表示起用金额（单位为分）")
    private Integer leastCost;

    @ApiModelProperty("代金券专用，表示减免金额（单位为分）")
    private Integer reduceCost;

    @ApiModelProperty("折扣券专用字段，表示打折额度（百分比）")
    private Integer discount;

    @ApiModelProperty("卡券封面")
    private String cover;

    @ApiModelProperty("适用产品")
    private CardLimitProductDto limitProduct;

    @ApiModelProperty("适用运营商")
    private List<CardLimitOperatorDto> limitOperatorList;

    @ApiModelProperty("卡券code")
    private String code;

    @ApiModelProperty("卡券是否可领取")
    private Boolean receivable;

    /**
     * 卡券生效时间
     */
    @JsonIgnore
    private Date validBeginTime;

    /**
     * 卡券过期时间
     */
    @JsonIgnore
    private Date validEndTime;

    /**
     * 可用时段
     */
    private List<CardTimeLimit> timeLimitList;

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

    public Integer getLeastCost() {
        return leastCost;
    }

    public void setLeastCost(Integer leastCost) {
        this.leastCost = leastCost;
    }

    public Integer getReduceCost() {
        return reduceCost;
    }

    public void setReduceCost(Integer reduceCost) {
        this.reduceCost = reduceCost;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public CardLimitProductDto getLimitProduct() {
        return limitProduct;
    }

    public void setLimitProduct(CardLimitProductDto limitProduct) {
        this.limitProduct = limitProduct;
    }

    public List<CardLimitOperatorDto> getLimitOperatorList() {
        return limitOperatorList;
    }

    public void setLimitOperatorList(List<CardLimitOperatorDto> limitOperatorList) {
        this.limitOperatorList = limitOperatorList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Boolean getReceivable() {
        return receivable;
    }

    public void setReceivable(Boolean receivable) {
        this.receivable = receivable;
    }

    public List<CardTimeLimit> getTimeLimitList() {
        return timeLimitList;
    }

    public void setTimeLimitList(List<CardTimeLimit> timeLimitList) {
        this.timeLimitList = timeLimitList;
    }

    public Date getValidBeginTime() {
        return validBeginTime;
    }

    public void setValidBeginTime(Date validBeginTime) {
        this.validBeginTime = validBeginTime;
    }

    public Date getValidEndTime() {
        return validEndTime;
    }

    public void setValidEndTime(Date validEndTime) {
        this.validEndTime = validEndTime;
    }
}
