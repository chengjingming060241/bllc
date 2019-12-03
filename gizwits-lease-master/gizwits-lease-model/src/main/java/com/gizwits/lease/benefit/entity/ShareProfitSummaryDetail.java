package com.gizwits.lease.benefit.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 分润账单汇总明细
 * </p>
 *
 * @author yinhui
 * @since 2018-05-05
 */
@TableName("share_profit_summary_detail")
public class ShareProfitSummaryDetail extends Model<ShareProfitSummaryDetail> {

    private static final long serialVersionUID = 1L;

    /**
     * 分润账单汇总ID
     */
    @TableId("summary_id")
	private String summaryId;
    /**
     * 分润账单号
     */
	@TableField("bill_no")
	private String billNo;


	public String getSummaryId() {
		return summaryId;
	}

	public void setSummaryId(String summaryId) {
		this.summaryId = summaryId;
	}

	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	@Override
	protected Serializable pkVal() {
		return this.summaryId;
	}

}
