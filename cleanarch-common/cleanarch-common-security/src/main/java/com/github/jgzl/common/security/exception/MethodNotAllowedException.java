package com.github.jgzl.common.security.exception;
import com.github.jgzl.common.security.component.CustomOauth2ExceptionSerializer;
import org.springframework.http.HttpStatus;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author lihaifeng
 * @date 2020/7/8
 */
@JsonSerialize(using = CustomOauth2ExceptionSerializer.class)
public class MethodNotAllowedException extends CustomOauth2Exception {

	public MethodNotAllowedException(String msg, Throwable t) {
		super(msg,t);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "method_not_allowed";
	}

	@Override
	public int getHttpErrorCode() {
		return HttpStatus.METHOD_NOT_ALLOWED.value();
	}
}
