package com.gizwits.lease.enums;

public enum WorkOrderStatus {
	INITIAL(1, "待受理"),
	ACCEPTED(2, "已受理"),
	PENDING(5, "待处理"),
	PROCESSING(6, "处理中"),
	COMPLETED(3, "已完成"),
	CLOSED(4, "已关闭"),;

	private Integer code;
	private String name;

	WorkOrderStatus(Integer code, String name) {
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

	public static WorkOrderStatus get(Integer code) {
		for (WorkOrderStatus workOrderStatus : values()) {
			if (code.equals(workOrderStatus.getCode())) return workOrderStatus;
		}
		return null;
	}
}
