package com.github.jgzl.common.security.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.jgzl.common.security.component.ExtendAuth2ExceptionSerializer;
import lombok.Getter;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * @author lihaifeng
 * @date 2018/7/8 自定义OAuth2Exception
 */
@JsonSerialize(using = ExtendAuth2ExceptionSerializer.class)
public class ExtendOauth2Exception extends OAuth2Exception {

	@Getter
	private String errorCode;

	public ExtendOauth2Exception(String msg) {
		super(msg);
	}

	public ExtendOauth2Exception(String msg, Throwable t) {
		super(msg, t);
	}

	public ExtendOauth2Exception(String msg, String errorCode) {
		super(msg);
		this.errorCode = errorCode;
	}

}
