package com.gizwits.boot.common;

/**
 * Processor - 处理器
 *
 * @author lilh
 * @date 2017/6/30 18:26
 */
public interface Processor<IN> {

    void process(IN in);
}
