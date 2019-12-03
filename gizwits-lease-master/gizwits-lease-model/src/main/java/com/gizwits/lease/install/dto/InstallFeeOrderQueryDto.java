package com.gizwits.lease.install.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class InstallFeeOrderQueryDto {

	private String orderNo;
	private String sno;
	private String mac;
	private String deviceName;
	private String productName;
	private String userName;
	private String userMobile;
	@JsonIgnore
	private Integer userId;
	@JsonIgnore
	private List<Integer> sysUserIds;

}
