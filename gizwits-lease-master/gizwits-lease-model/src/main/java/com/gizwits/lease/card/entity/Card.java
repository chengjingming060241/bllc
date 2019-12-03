package com.gizwits.lease.card.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 卡券
 * </p>
 *
 * @author ${author}
 * @since 2018-01-08
 */
public class Card extends Model<Card> {

    private static final long serialVersionUID = 1L;

    /**
     * 微信卡券ID
     */
    @TableId("card_id")
	private String cardId;
    /**
     * 卡券名
     */
	private String title;
    /**
     * 卡券类型。代金券：CASH; 折扣券：DISCOUNT; 年卡：YEAR; 体验卡：TRY
     */
	@TableField("card_type")
	private String cardType;
    /**
     * 卡券状态 CARD_STATUS_NOT_VERIFY:待审核 CARD_STATUS_VERIFY_FAIL:审核失败 CARD_STATUS_VERIFY_OK:通过审核 CARD_STATUS_DELETE:卡券被商户删除 CARD_STATUS_DISPATCH:在公众平台投放过的卡券
     */
	private String status;
    /**
     * 使用时间的类型 DATE_TYPE_FIX_TIME_RANGE 表示固定日期区间，DATE_TYPE_FIX_TERM表示固定时长（自领取后按天算）
     */
	@TableField("date_type")
	private String dateType;
    /**
     * DATE_TYPE_FIX_TIME_RANGE时专用 ，表示起用时间。从1970年1月1日00:00:00至起用时间的秒数。（单位为秒）
     */
	@TableField("date_begin_timestamp")
	private Date dateBeginTimestamp;
    /**
     * DATE_TYPE_FIX_TIME_RANGE时专用 ，表示结束时间。（单位为秒）
     */
	@TableField("date_end_timestamp")
	private Date dateEndTimestamp;
    /**
     * DATE_TYPE_FIX_TERM时专用 ，表示自领取后多少天内有效，领取后当天有效填写0。 （单位为天）
     */
	@TableField("date_fixed_term")
	private Integer dateFixedTerm;
    /**
     * DATE_TYPE_FIX_TERM时专用 ，表示自领取后多少天开始生效。（单位为天）
     */
	@TableField("date_fixed_begin_term")
	private Integer dateFixedBeginTerm;
    /**
     * 卡券现有库存的数量
     */
	private Integer quantity;
    /**
     * 代金券专用，表示起用金额（单位为分）
     */
	@TableField("least_cost")
	private Integer leastCost;
    /**
     * 代金券专用，表示减免金额（单位为分）
     */
	@TableField("reduce_cost")
	private Integer reduceCost;
    /**
     * 折扣券专用字段，表示打折额度（百分比）
     */
	private Integer discount;
    /**
     * 卡券创建者, 系统用户ID
     */
	@TableField("sys_user_id")
	private Integer sysUserId;
    /**
     * 微信投放 0:否, 1:是
     */
	@TableField("dispatch_web")
	private Integer dispatchWeb;
    /**
     * APP投放 0:否, 1:是
     */
	@TableField("dispatch_app")
	private Integer dispatchApp;
    /**
     * 卡券封面
     */
	private String cover;
    /**
     * 卡券展示顺序
     */
	private Integer sequence;
    /**
     * 卡券适用产品ID, NULL为全部产品适用
     */
	@TableField("product_id")
	private Integer productId;
    /**
     * 卡券适用运营商ID, NULL为全部运营商适用, 多个运营商ID使用,分隔
     */
	@TableField("operator_ids")
	private String operatorIds;
    /**
     * 添加时间
     */
	private Date ctime;
    /**
     * 更新时间
     */
	private Date utime;
    /**
     * 同步时间
     */
	@TableField("sync_time")
	private Date syncTime;
    /**
     * 每人可领券的数量限制
     */
	@TableField("receive_limit")
	private Integer receiveLimit;

	/**
	 * 可用时段 JSON数组
	 */
	@TableField("time_limit")
	private String timeLimit;

	/**
     * 套餐金额
     */
	@TableField("card_cost")
	private Integer cardCost;
    /**
     * 套餐使用次数
     */
	@TableField("usable_times")
	private Integer usableTimes;

    /**
     * 套餐默认赠送次数
     */
	@TableField("default_usage")
	private Integer defaultUsage;

	/**
	 * 分润次数
	 */
	@TableField("share_count")
	private Integer shareCount;

	/**
	 * 运营商充值次数
	 */
	@TableField("recharge_count")
	private Integer rechargeCount;

	/**
	 * 删除
	 */
	@TableField("is_deleted")
	private Integer delete;


	private String operatorName;

	/**
	 * 是否为代充卡
	 */
	@TableField("is_concession")
	private Boolean concession;

	public Boolean getConcession() {
		return concession;
	}

	public void setConcession(Boolean concession) {
		this.concession = concession;
	}

	public Integer getDelete() {
		return delete;
	}

	public void setDelete(Integer delete) {
		this.delete = delete;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
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

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public Integer getSequence() {
		return sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public String getOperatorIds() {
		return operatorIds;
	}

	public void setOperatorIds(String operatorIds) {
		this.operatorIds = operatorIds;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Date getUtime() {
		return utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public Date getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(Date syncTime) {
		this.syncTime = syncTime;
	}

	public Integer getReceiveLimit() {
		return receiveLimit;
	}

	public void setReceiveLimit(Integer receiveLimit) {
		this.receiveLimit = receiveLimit;
	}

	public String getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(String timeLimit) {
		this.timeLimit = timeLimit;
	}

	@Override
	protected Serializable pkVal() {
		return this.cardId;
	}

}
