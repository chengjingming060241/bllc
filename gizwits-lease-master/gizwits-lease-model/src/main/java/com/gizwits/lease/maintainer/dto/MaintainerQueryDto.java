package com.gizwits.lease.maintainer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Date;
import java.util.List;

public class MaintainerQueryDto {
	@JsonIgnore
	private Integer maintainerRoleId;
	@JsonIgnore
	private List<Integer> ownerIds;
	private String nickname;
	private String mobile;
	private String ownerName;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date startTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date endTime;

	public Integer getMaintainerRoleId() {
		return maintainerRoleId;
	}

	public void setMaintainerRoleId(Integer maintainerRoleId) {
		this.maintainerRoleId = maintainerRoleId;
	}

	public List<Integer> getOwnerIds() {
		return ownerIds;
	}

	public void setOwnerIds(List<Integer> ownerIds) {
		this.ownerIds = ownerIds;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
}
