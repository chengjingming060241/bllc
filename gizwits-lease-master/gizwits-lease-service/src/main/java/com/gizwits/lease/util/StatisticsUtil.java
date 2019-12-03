package com.gizwits.lease.util;

import com.gizwits.boot.utils.ParamUtil;

import java.math.BigDecimal;

public class StatisticsUtil {

	public static double calcIncreasePercent(Number pre, Number next) {
		return ParamUtil.isNullOrZero(pre) || next == null ? 0 :
				BigDecimal.valueOf(next.doubleValue() - pre.doubleValue())
						.divide(BigDecimal.valueOf(pre.doubleValue()), 2, BigDecimal.ROUND_HALF_UP)
						.doubleValue();
	}
}
