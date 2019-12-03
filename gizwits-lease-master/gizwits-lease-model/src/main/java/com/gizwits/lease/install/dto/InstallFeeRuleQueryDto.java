package com.gizwits.lease.install.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 初装费规则
 * </p>
 *
 * @author Joke
 * @since 2018-04-16
 */
@Data
public class InstallFeeRuleQueryDto {

    /**
     * 规则名称
     */
	private String name;
    /**
     * 适应产品名称
     */
	private String productName;
    /**
     * 创建人id
     */
    @JsonIgnore
	private List<Integer> sysUserIds;

}
