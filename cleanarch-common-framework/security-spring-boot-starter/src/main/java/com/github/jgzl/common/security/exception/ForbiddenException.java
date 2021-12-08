package com.github.jgzl.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.jgzl.common.security.component.ExtendAuth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

/**
 * @author lihaifeng
 * @date 2018/7/8
 */
@JsonSerialize(using = ExtendAuth2ExceptionSerializer.class)
public class ForbiddenException extends ExtendOauth2Exception {

	public ForbiddenException(String msg) {
		super(msg);
	}

	public ForbiddenException(String msg, Throwable t) {
		super(msg, t);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "access_denied";
	}

	@Override
	public int getHttpErrorCode() {
		return HttpStatus.FORBIDDEN.value();
	}

}
