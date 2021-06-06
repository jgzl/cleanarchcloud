package com.github.jgzl.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.constraints.NotBlank;

import com.github.jgzl.common.enums.OperateModule;
import org.hibernate.validator.constraints.Length;

/**
 * 日志记录注解,用来做日志埋点,这里主要的是操作日志的记录,
 * 而笼统的日志,使用log4j直接很方便的就可以入库(一般都是以文件的方式存储日志,不会入库)
 *
 * @author xuhang
 */
@Documented
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogRecord {

	/**
	 * 操作内容
	 *
	 * @return
	 */
	@NotBlank(message = "操作内容为必填项")
	@Length(max = 20, message = "操作内容过长")
	String operateContent();

	/**
	 * 操作模块
	 *
	 * @return
	 */
	@NotBlank(message = "操作模块为必填项")
    OperateModule operateModule();
}
