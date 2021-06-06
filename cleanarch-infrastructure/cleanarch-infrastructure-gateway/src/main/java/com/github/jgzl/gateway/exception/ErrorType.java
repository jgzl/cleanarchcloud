package com.github.jgzl.gateway.exception;

public interface ErrorType {
	/**
	 * 返回code
	 *
	 * @return
	 */
	String getCode();

	/**
	 * 返回mesg
	 *
	 * @return
	 */
	String getMesg();
}
