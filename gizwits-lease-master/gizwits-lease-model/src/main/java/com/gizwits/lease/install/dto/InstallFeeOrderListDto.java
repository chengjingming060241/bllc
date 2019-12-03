package com.gizwits.lease.install.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gizwits.boot.base.Constants;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class InstallFeeOrderListDto {

	private String orderNo;
	private String sno;
	private String mac;
	private String deviceName;
	private Integer ruleId;
	private String ruleName;
	private Integer productId;
	private String productName;
	private Integer userId;
	private String userName;
	private BigDecimal fee;
	private Integer status;
	private String statusDesc;
	private Integer payType;
	private String payTypeDesc;
	@JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
	private Date payTime;
	/**
	 * 创建时间
	 */
	@JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
	private Date ctime;
	/**
	 * 更新时间
	 */
	@JsonFormat(pattern = Constants.DEFAULT_DATE_PATTERN, timezone = "GMT+8")
	private Date utime;

}
