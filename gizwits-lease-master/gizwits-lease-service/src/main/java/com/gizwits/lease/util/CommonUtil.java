package com.gizwits.lease.util;

import com.alibaba.fastjson.JSONObject;
import com.gizwits.lease.product.entity.ProductServiceDetail;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;

/**
 * 通用工具类
 * Created by yuqing on 2017/10/26.
 */
public class CommonUtil {

    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

    /**
     * 获取ProductServiceDetail中Command记录的Available_Time
     * @param psd
     * @return 如果没有找到Available_Time，返回null
     */
    public static Integer getAvailableTime(ProductServiceDetail psd) {
        return getAvailableTime(psd.getCommand());
    }

    public static Integer getAvailableTime(String command) {
        if (StringUtils.isBlank(command)) {
            return null;
        }

        try {
            JSONObject tmp = JSONObject.parseObject(command);
            return tmp.getInteger("Available_Time");
        } catch (Exception e) {
            logger.error(command, e);
        }

        return null;
    }

    /**
     * 四舍五入的圆整策略
     * @return
     */
    public static int defaultRoundPolicy() {
        return BigDecimal.ROUND_HALF_UP;
    }

    /**
     * 2个精度的圆整
     * @param value
     * @return
     */
    public static BigDecimal round(BigDecimal value) {
        return round(value, 2);
    }

    /**
     * scale个精度的圆整
     * @param value
     * @param scale
     * @return
     */
    public static BigDecimal round(BigDecimal value, int scale) {
        return value.setScale(scale, defaultRoundPolicy());
    }

    /**
     * scale个精度的圆整
     * @param value
     * @param scale
     * @return
     */
    public static BigDecimal round(double value, int scale) {
        return round(new BigDecimal(value), scale);
    }

    /**
     * 判断value是否为0
     * @param value
     * @return
     */
    public static boolean isZero(BigDecimal value) {
        return value.compareTo(BigDecimal.ZERO) == 0;
    }

    /**
     * 判断t是否为null或者空的数组，集合，映射
     * @param t
     * @param <T>
     * @return
     */
    public static <T> boolean isNullOrEmpty(T t) {
        if (t == null) {
            return true;
        }
        if (t.getClass().isArray() && isEmptyArray(t)) {
            return true;
        }
        if (t instanceof Collection && isEmptyCollection((Collection)t)) {
            return true;
        }
        if (t instanceof Map && isEmptyMap((Map)t)) {
            return true;
        }
        return false;
    }

    public static boolean isEmptyCollection(Collection collection) {
        return collection.size() == 0;
    }

    public static boolean isEmptyMap(Map map) {
        return map.size() == 0;
    }

    public static <T> boolean isEmptyArray(T t) {
        if (t == null || !t.getClass().isArray()) {
            throw new IllegalArgumentException("argument is null or not array type.");
        }

        return Array.getLength(t) == 0;
    }
}
