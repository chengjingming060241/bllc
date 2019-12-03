package com.gizwits.lease.install.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 初装费订单
 * </p>
 *
 * @author Joke
 * @since 2018-04-16
 */
@TableName("install_fee_order")
public class InstallFeeOrder extends Model<InstallFeeOrder> {

    private static final long serialVersionUID = 1L;

    @TableId("order_no")
	private String orderNo;
    /**
     * 设备sno
     */
	private String sno;
    /**
     * 设备mac
     */
	private String mac;
    /**
     * 规则id
     */
	@TableField("rule_id")
	private Integer ruleId;
    /**
     * 适应产品id
     */
	@TableField("product_id")
	private Integer productId;
    /**
     * 装机金额
     */
	private BigDecimal fee;
    /**
     * 状态
     */
	private Integer status;
    /**
     * 支付类型
     */
	@TableField("pay_type")
	private Integer payType;
    /**
     * 支付时间
     */
	@TableField("pay_time")
	private Date payTime;
    /**
     * 用户id
     */
	@TableField("user_id")
	private Integer userId;
    /**
     * 用户id
     */
	@TableField("sys_user_id")
	private Integer sysUserId;
    /**
     * 创建时间
     */
	private Date ctime;
    /**
     * 更新时间
     */
	private Date utime;
    /**
     * 是否删除
     */
	private Boolean del;


	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public Integer getRuleId() {
		return ruleId;
	}

	public void setRuleId(Integer ruleId) {
		this.ruleId = ruleId;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
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

	public Boolean isDel() {
		return del;
	}

	public void setDel(Boolean del) {
		this.del = del;
	}

	@Override
	protected Serializable pkVal() {
		return this.orderNo;
	}

}
