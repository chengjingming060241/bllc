package com.gizwits.lease.enums;

public enum WorkOrderSource {
	MANUAL(1, "后台录入"),
	TRANSFORM(2, "故障生成"),
	APP_REPORT(3, "APP反馈"),
	WX_REPORT(4, "微信反馈"),
	ABNORMAL_ORDER(5, "异常订单");

	private Integer code;
	private String name;

	WorkOrderSource(Integer code, String name) {
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

	public static WorkOrderSource get(Integer code) {
		for (WorkOrderSource workOrderSource : values()) {
			if (code.equals(workOrderSource.getCode())) return workOrderSource;
		}
		return null;
	}
}
