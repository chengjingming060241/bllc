package com.gizwits.lease.enums;

public enum ThirdPartyLoginType {
	WX(1,"微信"),
	TENCENT(2,"腾讯"),
	;

	private Integer code;
	private String name;

	ThirdPartyLoginType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static ThirdPartyLoginType get(Integer code) {
		for (ThirdPartyLoginType workOrderType : values()) {
			if (code.equals(workOrderType.getCode())) return workOrderType;
		}
		return null;
	}
}
