package com.github.jgzl.common.security.exception;
import com.github.jgzl.common.security.component.CustomOauth2ExceptionSerializer;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author lihaifeng
 * @date 2020/7/8
 */
@JsonSerialize(using = CustomOauth2ExceptionSerializer.class)
public class ForbiddenException extends CustomOauth2Exception {

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

