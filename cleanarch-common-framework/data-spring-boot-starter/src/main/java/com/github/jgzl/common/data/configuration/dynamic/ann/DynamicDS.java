package com.github.jgzl.common.data.configuration.dynamic.ann;


import java.lang.annotation.*;

/**
 * 如果租户类型为 字段隔离 请注释  @DS("#custom.tenant_code")
 * todo 从登陆上下问取感觉意义不大的样子
 * 自定义动态切换
 * 上下文 -> header -> session -> expression
 *
 * @author lihaifeng
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@DS("#custom.tenant_code")
public @interface DynamicDS {

}
