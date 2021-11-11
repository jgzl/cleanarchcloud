package com.github.jgzl.common.core.annotation.log;
import java.lang.annotation.*;

/**
 * @author lihaifeng
 * @date 2020/6/28 操作日志注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {

	/**
	 * 描述
	 *
	 * @return {String}
	 */
	String value();

	/**
	 * 记录执行参数
	 *
	 * @return true | false
	 */
	boolean request() default true;

	/**
	 * 当 request = false时， 需要方法报错是否记录请求参数
	 *
	 * @return true | false
	 */
	boolean requestByError() default true;

	/**
	 * 记录返回参数
	 *
	 * @return true | false
	 */
	boolean response() default false;
}

