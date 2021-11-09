package com.github.jgzl.common.security.exception;
import com.github.jgzl.common.security.component.CustomOauth2ExceptionSerializer;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author lihaifeng
 * @date 2020/7/8
 */
@JsonSerialize(using = CustomOauth2ExceptionSerializer.class)
public class UnauthorizedException extends CustomOauth2Exception {

	public UnauthorizedException(String msg, Throwable t) {
		super(msg,t);
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
