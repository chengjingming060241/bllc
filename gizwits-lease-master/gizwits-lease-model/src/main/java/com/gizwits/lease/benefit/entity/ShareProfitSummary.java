package com.gizwits.lease.benefit.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 分润账单汇总
 * </p>
 *
 * @author yinhui
 * @since 2018-05-05
 */
@TableName("share_profit_summary")
public class ShareProfitSummary extends Model<ShareProfitSummary> {

    private static final long serialVersionUID = 1L;

    /**
     * 分润账单汇总ID
     */
	private String id;
    /**
     * 分润批次ID
     */
	@TableField("batch_id")
	private Integer batchId;
    /**
     * 用户及其下级的分润账单汇总
     */
	@TableField("sys_user_id")
	private Integer sysUserId;
    /**
     * 订单总数
     */
	@TableField("order_count")
	private Integer orderCount;
    /**
     * 订单总金额
     */
	@TableField("order_money")
	private BigDecimal orderMoney;
    /**
     * 分润账单总金额
     */
	@TableField("share_money")
	private BigDecimal shareMoney;
    /**
     * 状态 1:未支付 2:部分已支付 3:已完成
     */
	private Integer status;
    /**
     * 创建时间
     */
	private Date ctime;
    /**
     * 更新时间
     */
	private Date utime;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getBatchId() {
		return batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public BigDecimal getOrderMoney() {
		return orderMoney;
	}

	public void setOrderMoney(BigDecimal orderMoney) {
		this.orderMoney = orderMoney;
	}

	public BigDecimal getShareMoney() {
		return shareMoney;
	}

	public void setShareMoney(BigDecimal shareMoney) {
		this.shareMoney = shareMoney;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
