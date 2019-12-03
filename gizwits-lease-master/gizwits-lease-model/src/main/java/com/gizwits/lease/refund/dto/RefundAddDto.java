package com.gizwits.lease.refund.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 退款申请表
 * </p>
 *
 * @author Joke
 * @since 2018-02-08
 */
public class RefundAddDto {
	@NotBlank
	private String orderNo;
	private String userMobile;
	private String userAlipayAccount;
	private String userAlipayRealName;
	@NotBlank
	private String refundReason;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getUserMobile() {
		return userMobile;
	}

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserAlipayAccount() {
		return userAlipayAccount;
	}

	public void setUserAlipayAccount(String userAlipayAccount) {
		this.userAlipayAccount = userAlipayAccount;
	}

	public String getUserAlipayRealName() {
		return userAlipayRealName;
	}

	public void setUserAlipayRealName(String userAlipayRealName) {
		this.userAlipayRealName = userAlipayRealName;
	}

	public String getRefundReason() {
		return refundReason;
	}

	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}
}
