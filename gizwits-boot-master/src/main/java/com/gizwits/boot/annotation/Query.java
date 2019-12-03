package com.gizwits.boot.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation - 查询过虑
 *
 * @author lilh
 * @date 2017/7/5 14:23
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Inherited
@Documented
public @interface Query {

    /**
     * 查询字段，跟数据库中一致
     */
    String field();

    /**
     * 操作符
     */
    Operator operator() default Operator.eq;

    /**
     * operator 为isNull和isNotNull时有效,字段必须为String
     */
    String condition() default "";

    /**
     * operator为isNull和isNotNull时有效
     */
    boolean mutex() default false;


    enum Operator {

        /** 等于 */
        eq,

        /** 不等于 */
        ne,

        /** 大于 */
        gt,

        /** 小于 */
        lt,

        /** 大于等于 */
        ge,

        /** 小于等于 */
        le,

        /** 相似 */
        like,

        /** 包含 */
        in,

        /** 不包含 */
        notIn,

        /** 为Null */
        isNull,

        /** 不为Null */
        isNotNull
    }

}
