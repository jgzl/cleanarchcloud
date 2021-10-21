package com.github.jgzl.infra.upms.custom.login.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * 自定义 OAuth2异常
 *
 * @author lihaifeng
 * 2019/5/6 10:27
 */
@JsonSerialize(using = CustomOauth2ExceptionSerializer.class)
public class CustomOauth2Exception extends OAuth2Exception {

    private static final long serialVersionUID = -1003326583561699922L;

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
