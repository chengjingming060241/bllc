package com.gizwits.lease.benefit.entity;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 分润规则修改次数限制
 * </p>
 *
 * @author 
 * @since 2017-12-15
 */
@TableName("share_benefit_rule_modify_limit")
public class ShareBenefitRuleModifyLimit extends Model<ShareBenefitRuleModifyLimit> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键,自增长
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 添加时间
     */
	private Date ctime;
    /**
     * 更新时间
     */
	private Date utime;
    /**
     * 创建人(厂商绑定的系统帐号)
     */
	@TableField("sys_user_id")
	private Integer sysUserId;
    /**
     * 首次账单生成前可修改次数
     */
	@TableField("before_bill_limit")
	private Integer beforeBillLimit;
    /**
     * 周期单位 1:每年, 2:每月
     */
	@TableField("period_unit")
	private Integer periodUnit;
    /**
     * 周期内可修改次数
     */
	@TableField("period_limit")
	private Integer periodLimit;
    /**
     * 可允许修改时段 开始时间
     */
	@TableField("start_time")
	private Date startTime;
    /**
     * 可允许修改时段 结束时间
     */
	@TableField("end_time")
	private Date endTime;
    /**
     * 可允许修改时段 1:(星期一) ... 7:(星期日)
     */
	private String weekdays;


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

	public Date getUtime() {
		return utime;
	}

	public void setUtime(Date utime) {
		this.utime = utime;
	}

	public Integer getSysUserId() {
		return sysUserId;
	}

	public void setSysUserId(Integer sysUserId) {
		this.sysUserId = sysUserId;
	}

	public Integer getBeforeBillLimit() {
		return beforeBillLimit;
	}

	public void setBeforeBillLimit(Integer beforeBillLimit) {
		this.beforeBillLimit = beforeBillLimit;
	}

	public Integer getPeriodUnit() {
		return periodUnit;
	}

	public void setPeriodUnit(Integer periodUnit) {
		this.periodUnit = periodUnit;
	}

	public Integer getPeriodLimit() {
		return periodLimit;
	}

	public void setPeriodLimit(Integer periodLimit) {
		this.periodLimit = periodLimit;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getWeekdays() {
		return weekdays;
	}

	public void setWeekdays(String weekdays) {
		this.weekdays = weekdays;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
