package com.gizwits.lease.wallet.dto;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class RechargeRuleForEditDto {

	@NotEmpty
	private List<RechargeMoneyDto> details;

	@NotNull
	private BigDecimal rate;


	public List<RechargeMoneyDto> getDetails() {
		return details;
	}

	public void setDetails(List<RechargeMoneyDto> details) {
		this.details = details;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
}
