package com.gizwits.lease.enums;

public enum WorkOrderType {
	INSTALL(1,"装机"),
	MAINTAIN(2,"维护"),
	UNINSTALL(3,"拆换"),
	;

	private Integer code;
	private String name;

	WorkOrderType(Integer code, String name) {
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

	public static WorkOrderType get(Integer code) {
		for (WorkOrderType workOrderType : values()) {
			if (code.equals(workOrderType.getCode())) return workOrderType;
		}
		return null;
	}
}
