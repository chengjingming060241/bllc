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
 * 分润批次
 * </p>
 *
 * @author yinhui
 * @since 2018-05-05
 */
@TableName("share_profit_batch")
public class ShareProfitBatch extends Model<ShareProfitBatch> {

    private static final long serialVersionUID = 1L;

    /**
     * 分润批次ID
     */
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 账单生成周期 DAY,WEEK,MONTH,YEAR
     */
	private String frequency;
    /**
     * 周期开始时间
     */
	@TableField("period_start_time")
	private Date periodStartTime;
    /**
     * 生成时间 周期结束时间
     */
	@TableField("period_end_time")
	private Date periodEndTime;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Date getPeriodStartTime() {
		return periodStartTime;
	}

	public void setPeriodStartTime(Date periodStartTime) {
		this.periodStartTime = periodStartTime;
	}

	public Date getPeriodEndTime() {
		return periodEndTime;
	}

	public void setPeriodEndTime(Date periodEndTime) {
		this.periodEndTime = periodEndTime;
	}

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
