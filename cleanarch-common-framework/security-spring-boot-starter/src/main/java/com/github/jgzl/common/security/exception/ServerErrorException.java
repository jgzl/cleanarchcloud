package com.github.jgzl.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.jgzl.common.security.component.ExtendAuth2ExceptionSerializer;
import org.springframework.http.HttpStatus;

/**
 * @author lihaifeng
 * @date 2018/7/8
 */
@JsonSerialize(using = ExtendAuth2ExceptionSerializer.class)
public class ServerErrorException extends ExtendOauth2Exception {

	public ServerErrorException(String msg, Throwable t) {
		super(msg);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return "server_error";
	}

	@Override
	public int getHttpErrorCode() {
		return HttpStatus.INTERNAL_SERVER_ERROR.value();
	}

}
