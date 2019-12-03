package com.gizwits.lease.stat.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 订单分析统计表
 * </p>
 *
 * @author Joke
 * @since 2018-02-01
 */
@TableName("stat_order")
public class StatOrder extends Model<StatOrder> {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键,自增
	 */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
	/**
	 * 添加时间
	 */
	private Date ctime;
	/**
	 * 归属系统用户id
	 */
	@TableField("sys_user_id")
	private Integer sysUserId;
	/**
	 * 设备序列号
	 */
	private String sno;
	/**
	 * 订单总金额
	 */
	@TableField("order_amount")
	private Double orderAmount;
	/**
	 * 订单数量
	 */
	@TableField("order_count")
	private Integer orderCount;

	@TableField("product_id")
	private Integer productId;
	/**
	 * 代理商id
	 */
	@TableField("agent_id")
	private Integer agentId;
	/**
	 * 运营商id
	 */
	@TableField("operator_id")
	private Integer operatorId;
	/**
	 * 投放点id
	 */
	@TableField("launch_area_id")
	private Integer launchAreaId;
	/**
	 * 退款数
	 */
	@TableField("refund_count")
	private Integer refundCount;
	/**
	 * 退款金额
	 */
	@TableField("refund_amount")
	private Double refundAmount;
	/**
	 * 已生成分润单的分润金额
	 */
	@TableField("generated_share_amount")
	private Double generatedShareAmount;
	/**
	 * 未生成分润单的订单金额
	 */
	@TableField("ungenerate_share_order_amount")
	private Double ungenerateShareOrderAmount;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
	}

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public Double getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(Double orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public Integer getProductId() {
		return productId;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public Integer getAgentId() {
		return agentId;
	}

	public void setAgentId(Integer agentId) {
		this.agentId = agentId;
	}

	public Integer getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}

	public Integer getLaunchAreaId() {
		return launchAreaId;
	}

	public void setLaunchAreaId(Integer launchAreaId) {
		this.launchAreaId = launchAreaId;
	}

	public Integer getRefundCount() {
		return refundCount;
	}

	public void setRefundCount(Integer refundCount) {
		this.refundCount = refundCount;
	}

	public Double getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(Double refundAmount) {
		this.refundAmount = refundAmount;
	}

	public Double getGeneratedShareAmount() {
		return generatedShareAmount;
	}

	public void setGeneratedShareAmount(Double generatedShareAmount) {
		this.generatedShareAmount = generatedShareAmount;
	}

	public Double getUngenerateShareOrderAmount() {
		return ungenerateShareOrderAmount;
	}

	public void setUngenerateShareOrderAmount(Double ungenerateShareOrderAmount) {
		this.ungenerateShareOrderAmount = ungenerateShareOrderAmount;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
