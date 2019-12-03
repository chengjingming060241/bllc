package com.gizwits.lease.benefit.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 订单的分润信息
 * </p>
 *
 * @author yinhui
 * @since 2018-05-05
 */
@TableName("order_share_profit")
public class OrderShareProfit extends Model<OrderShareProfit> {

    private static final long serialVersionUID = 1L;

    /**
     * 订单号
     */
    @TableId("order_no")
	private String orderNo;
    /**
     * 分润层级 总部:1
     */
	@TableField("share_profit_level")
	private Integer shareProfitLevel;
    /**
     * 分润对象用户
     */
	@TableField("share_profit_user")
	private Integer shareProfitUser;
    /**
     * 订单金额
     */
	@TableField("order_money")
	private BigDecimal orderMoney;
    /**
     * 分润比例 0~100
     */
	@TableField("share_profit_percent")
	private BigDecimal shareProfitPercent;
    /**
     * 分润金额
     */
	@TableField("share_money")
	private BigDecimal shareMoney;
    /**
     * 分润账单号
     */
	@TableField("share_profit_bill_no")
	private String shareProfitBillNo;
    /**
     * 创建时间
     */
	private Date ctime;
    /**
     * 更新时间
     */
	private Date utime;
	/**
	 * 分润状态
	 */
	private Integer status;
	/**
	 * 支付类型
	 */
	@TableField("pay_type")
	private Integer payType;

	@TableField("trade_no")
	private String tradeNo;
	/**
	 * 分润成功时的微信订单号
	 */
	@TableField("payment_no")
	private String paymentNo;
	/**
	 * 微信付款成功时间
	 */
	@TableField("payment_time")
	private Date paymentTime;

	/**
	 * 是否使用trade_no重试支付，如果此字段为6，则分润单不可修改，只再次支付
	 */
	@TableField("is_try_again")
	private Integer isTryAgain;
	/**
	 * 是否生成分润单
	 */
	@TableField("is_generate")
	private Integer isGenerate;

	/**
	 * 所属人姓名
	 */
	private String personal;
	/**
	 * 分润结果
	 */
	@TableField("share_benefit_result")
	private String shareBenefitResult;

	/**
	 * 被推荐商家id
	 */
	@TableField("recommended_person_store_id")
	private Integer recommendedPersonStoreId;

	public Integer getRecommendedPersonStoreId() {
		return recommendedPersonStoreId;
	}

	public void setRecommendedPersonStoreId(Integer recommendedPersonStoreId) {
		this.recommendedPersonStoreId = recommendedPersonStoreId;
	}

	public String getPersonal() { return personal; }

	public void setPersonal(String personal) { this.personal = personal; }

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getShareProfitLevel() {
		return shareProfitLevel;
	}

	public void setShareProfitLevel(Integer shareProfitLevel) {
		this.shareProfitLevel = shareProfitLevel;
	}

	public Integer getShareProfitUser() {
		return shareProfitUser;
	}

	public void setShareProfitUser(Integer shareProfitUser) {
		this.shareProfitUser = shareProfitUser;
	}

	public BigDecimal getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}

	public BigDecimal getShareProfitPercent() {
		return shareProfitPercent;
	}

	public void setShareProfitPercent(BigDecimal shareProfitPercent) {
		this.shareProfitPercent = shareProfitPercent;
	}

	public BigDecimal getShareMoney() {
		return shareMoney;
	}

	public void setShareMoney(BigDecimal shareMoney) {
		this.shareMoney = shareMoney;
	}

	public String getShareProfitBillNo() {
		return shareProfitBillNo;
	}

	public void setShareProfitBillNo(String shareProfitBillNo) {
		this.shareProfitBillNo = shareProfitBillNo;
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

	public Integer getStatus() { return status; }

	public void setStatus(Integer status) { this.status = status; }

	public Integer getPayType() { return payType; }

	public void setPayType(Integer payType) { this.payType = payType; }

	public String getTradeNo() { return tradeNo; }

	public void setTradeNo(String tradeNo) { this.tradeNo = tradeNo; }

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public Date getPaymentTime() {
		return paymentTime;
	}

	public void setPaymentTime(Date paymentTime) {
		this.paymentTime = paymentTime;
	}

	public Integer getIsTryAgain() {
		return isTryAgain;
	}

	public void setIsTryAgain(Integer isTryAgain) {
		this.isTryAgain = isTryAgain;
	}

	public Integer getIsGenerate() { return isGenerate; }

	public void setIsGenerate(Integer isGenerate) { this.isGenerate = isGenerate; }

	public String getShareBenefitResult() { return shareBenefitResult; }

	public void setShareBenefitResult(String shareBenefitResult) { this.shareBenefitResult = shareBenefitResult; }

	@Override
	protected Serializable pkVal() {
		return this.orderNo;
	}

}
