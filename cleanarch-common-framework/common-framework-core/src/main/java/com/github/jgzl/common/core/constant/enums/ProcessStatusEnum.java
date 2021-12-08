package com.github.jgzl.common.core.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lihaifeng
 * @date 2018/9/30 流程状态
 */
@Getter
@AllArgsConstructor
public enum ProcessStatusEnum {

	/**
	 * 激活
	 */
	ACTIVE("active"),

	/**
	 * 暂停
	 */
	SUSPEND("suspend");

	/**
	 * 状态
	 */
	private final String status;

}
