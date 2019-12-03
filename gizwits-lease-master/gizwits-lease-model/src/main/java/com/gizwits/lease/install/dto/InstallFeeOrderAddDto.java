package com.gizwits.lease.install.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gizwits.lease.user.entity.User;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class InstallFeeOrderAddDto {
	@NotBlank
	private String sno;
	@JsonIgnore
	private User user;

}
