package com.gizwits.lease.install.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gizwits.boot.sys.entity.SysUser;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class InstallFeeRuleAddDto {
	@NotBlank
	private String name;
	@NotNull
	private Integer productId;
	@NotNull
	@Min(0)
	private BigDecimal fee;
	@JsonIgnore
	private SysUser sysUser;
}
