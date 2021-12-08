package com.github.jgzl.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.jgzl.common.security.component.ExtendAuth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

/**
 * @author lihaifeng
 * @date 2021-08-05
 * <p>
 * 令牌不合法
 */
@JsonSerialize(using = ExtendAuth2ExceptionSerializer.class)
public class TokenInvalidException extends ExtendOauth2Exception {

	public TokenInvalidException(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "invalid_token";
	}

	@Override
	public int getHttpErrorCode() {
		return HttpStatus.FAILED_DEPENDENCY.value();
	}

}
