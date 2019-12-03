package com.gizwits.lease.utils;

public class ObjectUtils {

	public static <V> V ifNull(V v1, V v2) {
		return v1 == null ? v2 : v1;
	}

}
