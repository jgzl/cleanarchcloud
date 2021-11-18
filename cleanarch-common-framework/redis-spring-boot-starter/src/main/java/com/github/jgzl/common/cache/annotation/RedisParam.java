package com.github.jgzl.common.cache.annotation;

import java.lang.annotation.*;

/**
 * 缓存 Key 的参数
 *
 * @author lihaifeng
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RedisParam {

    /**
     * 字段名称
     *
     * @return String
     */
    String name() default "";
}