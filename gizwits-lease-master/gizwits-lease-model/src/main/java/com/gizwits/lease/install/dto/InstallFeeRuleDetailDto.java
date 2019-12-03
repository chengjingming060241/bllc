package com.gizwits.lease.install.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gizwits.boot.base.Constants;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 初装费规则
 * </p>
 *
 * @author Joke
 * @since 2018-04-16
 */
@Data
public class InstallFeeRuleDetailDto {


	private Integer id;
    /**
     * 规则名称
     */
	private String name;
    /**
     * 适应产品id
     */
	private Integer productId;
    /**
     * 适应产品名称
     */
	private String productName;
    /**
     * 装机金额
     */
	private BigDecimal fee;
    /**
     * 创建人id
     */
	private Integer sysUserId;
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
