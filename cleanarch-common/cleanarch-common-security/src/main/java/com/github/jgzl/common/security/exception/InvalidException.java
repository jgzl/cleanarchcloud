package com.github.jgzl.common.security.exception;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.jgzl.common.security.component.CustomOauth2ExceptionSerializer;

/**
 * @author lihaifeng
 * @date 2018/7/8
 */
@JsonSerialize(using = CustomOauth2ExceptionSerializer.class)
public class InvalidException extends CustomOauth2Exception {

	public InvalidException(String msg, Throwable t) {
		super(msg,t);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "invalid_exception";
	}

	@Override
	public int getHttpErrorCode() {
		return 426;
	}
}
