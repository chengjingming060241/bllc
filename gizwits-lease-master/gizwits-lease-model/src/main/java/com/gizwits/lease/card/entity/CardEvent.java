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
 * 微信卡券领取事件
 * </p>
 *
 * @author ${author}
 * @since 2018-03-15
 */
@TableName("card_event")
public class CardEvent extends Model<CardEvent> {

    private static final long serialVersionUID = 1L;

	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 卡券的微信号
     */
	@TableField("wx_id")
	private String wxId;
    /**
     * 卡券ID
     */
	@TableField("card_id")
	private String cardId;
    /**
     * 领券用户的openid
     */
	@TableField("user_openid")
	private String userOpenid;
    /**
     * 卡券code
     */
	private String code;
    /**
     * 消息创建时间
     */
	@TableField("event_time")
	private Date eventTime;
    /**
     * 是否为转赠领取，1代表是，0代表否
     */
	@TableField("is_give_by_friend")
	private Integer isGiveByFriend;
    /**
     * 代表发起转赠用户的openid
     */
	@TableField("friend_openid")
	private String friendOpenid;
    /**
     * 代表转赠前的卡券code
     */
	@TableField("old_code")
	private String oldCode;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWxId() {
		return wxId;
	}

	public void setWxId(String wxId) {
		this.wxId = wxId;
	}

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getUserOpenid() {
		return userOpenid;
	}

	public void setUserOpenid(String userOpenid) {
		this.userOpenid = userOpenid;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getEventTime() {
		return eventTime;
	}

	public void setEventTime(Date eventTime) {
		this.eventTime = eventTime;
	}

	public Integer getIsGiveByFriend() {
		return isGiveByFriend;
	}

	public void setIsGiveByFriend(Integer isGiveByFriend) {
		this.isGiveByFriend = isGiveByFriend;
	}

	public String getFriendOpenid() {
		return friendOpenid;
	}

	public void setFriendOpenid(String friendOpenid) {
		this.friendOpenid = friendOpenid;
	}

	public String getOldCode() {
		return oldCode;
	}

	public void setOldCode(String oldCode) {
		this.oldCode = oldCode;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
