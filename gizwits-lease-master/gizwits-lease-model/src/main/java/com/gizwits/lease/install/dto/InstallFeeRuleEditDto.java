package com.gizwits.lease.install.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class InstallFeeRuleEditDto {
	@NotNull
	private Integer id;
	private String name;
	private Integer productId;
	@Min(0)
	private BigDecimal fee;
}
