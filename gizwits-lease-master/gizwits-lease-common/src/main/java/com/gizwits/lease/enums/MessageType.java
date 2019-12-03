package com.gizwits.lease.enums;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum MessageType {
	WORK_ORDER_MESSAGE(1, "工单消息"),
	DEVICE_MESSAGE(2,"设备消息"),
	SHARE_BENEFIT_MESSAGE(3,"分润账单消息"),
	LEASE_MESSAGE(4,"租赁消息"),
	SYS_MESSAGE(5,"系统消息"),
	;

	private Integer code;
	private String name;

	static Map<Integer,String> map;

	static {
		map = Arrays.stream(MessageType.values()).collect(Collectors.toMap(item -> item.code, item -> item.name));
	}

	public static String getName(Integer code){
		return map.get(code);
	}
	MessageType(Integer code, String name) {
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
}