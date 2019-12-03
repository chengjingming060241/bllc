package com.gizwits.lease.card.entity;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 卡券使用次数记录
 * </p>
 *
 * @author ${author}
 * @since 2018-02-08
 */
@TableName("card_consume_record")
public class CardConsumeRecord extends Model<CardConsumeRecord> {

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
     * 使用次数
     */
	@TableField("consume_count")
	private Integer consumeCount;


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

	public Integer getConsumeCount() {
		return consumeCount;
	}

	public void setConsumeCount(Integer consumeCount) {
		this.consumeCount = consumeCount;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
