package com.github.jgzl.common.excel.annotation;

import com.github.jgzl.common.excel.handler.DefaultAnalysisEventListener;
import com.github.jgzl.common.excel.handler.ListAnalysisEventListener;

import java.lang.annotation.*;

/**
 * 导入excel
 *
 * @author lihaifeng
 * @author L.cm
 * @date 2021/4/16
 */
@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestExcel {

	/**
	 * 前端上传字段名称 file
	 */
	String fileName() default "file";

	/**
	 * 读取的监听器类
	 *
	 * @return readListener
	 */
	Class<? extends ListAnalysisEventListener<?>> readListener() default DefaultAnalysisEventListener.class;

	/**
	 * 是否跳过空行
	 *
	 * @return 默认跳过
	 */
	boolean ignoreEmptyRow() default false;

}
