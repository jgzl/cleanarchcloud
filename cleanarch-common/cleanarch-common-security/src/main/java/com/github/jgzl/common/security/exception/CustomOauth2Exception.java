package com.github.jgzl.common.security.exception;

import com.github.jgzl.common.security.component.CustomOauth2ExceptionSerializer;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author lihaifeng
 * @date 2018/7/8
 * 自定义OAuth2Exception
 */
@JsonSerialize(using = CustomOauth2ExceptionSerializer.class)
public class CustomOauth2Exception extends OAuth2Exception {

	private String oauth2ErrorCode;

	public CustomOauth2Exception(final String msg, final Throwable t) {
		super(msg, t);
	}

	@Override
	public String getOAuth2ErrorCode() {
		return this.oauth2ErrorCode;
	}

	public void setOauth2ErrorCode(final String oauth2ErrorCode) {
		this.oauth2ErrorCode = oauth2ErrorCode;
	}
}
