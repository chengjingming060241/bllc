package com.gizwits.lease.card.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户的卡券
 * </p>
 *
 * @author ${author}
 * @since 2018-01-24
 */
@TableName("user_card")
public class UserCard extends Model<UserCard> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 用户ID
     */
	@TableField("user_id")
	private Integer userId;
    /**
     * 卡券ID
     */
	@TableField("card_id")
	private String cardId;
    /**
     * 卡券code
     */
	private String code;
    /**
     * 办理渠道 1:微信自助办理 2:线下全额代充值  3.线下让利代充值
     */
	@TableField("receive_channel")
	private Integer receiveChannel;

	/**
	 * 年卡:YEAR,体验卡:TRY,让利代充:CONCESSION
	 */
	@TableField("card_type")
	private String cardType;
    /**
     * 卡券状态 1:正常 2:已使用 3:已失效
     */
	private Integer status;
    /**
     * 卡券领取时间
     */
	@TableField("received_time")
	private Date receivedTime;
    /**
     * 卡券使用时间
     */
	@TableField("consumed_time")
	private Date consumedTime;
    /**
     * 卡券生效时间
     */
	@TableField("begin_time")
	private Date beginTime;
    /**
     * 卡券过期时间
     */
	@TableField("end_time")
	private Date endTime;
    /**
     * 数据创建时间
     */
	private Date ctime;

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
	 * 套餐金额
	 */
	@TableField("card_cost")
	private Integer cardCost;


	/**
	 * 是否为代充卡
	 */
	@TableField("is_concession")
	private boolean isConcession;


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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getReceiveChannel() {
		return receiveChannel;
	}

	public void setReceiveChannel(Integer receiveChannel) {
		this.receiveChannel = receiveChannel;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getReceivedTime() {
		return receivedTime;
	}

	public void setReceivedTime(Date receivedTime) {
		this.receivedTime = receivedTime;
	}

	public Date getConsumedTime() {
		return consumedTime;
	}

	public void setConsumedTime(Date consumedTime) {
		this.consumedTime = consumedTime;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
