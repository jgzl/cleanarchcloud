package com.github.jgzl.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.jgzl.common.security.component.ExtendAuth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

/**
 * @author lihaifeng
 * @date 2018/7/8
 */
@JsonSerialize(using = ExtendAuth2ExceptionSerializer.class)
public class UnauthorizedException extends ExtendOauth2Exception {

	public UnauthorizedException(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "unauthorized";
	}

	@Override
	public int getHttpErrorCode() {
		return HttpStatus.UNAUTHORIZED.value();
	}

}
