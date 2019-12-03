package com.gizwits.lease.install.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gizwits.boot.sys.entity.SysUser;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Data
public class InstallFeeRuleLinkDto {
	@NotNull
	private Integer ruleId;
	private List<Integer> agentIds;
	private List<Integer> operatorIds;
	@JsonIgnore
	private SysUser sysUser;
}
