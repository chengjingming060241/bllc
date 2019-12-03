package com.gizwits.boot.common;

/**
 * Translator - 转换器
 *
 * @author lilh
 * @date 2017/6/30 18:25
 */
public interface Translator<IN, OUT> {

    OUT translate(IN in);
}
