package com.github.jgzl.application.auth.mvc.filter;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.github.jgzl.application.auth.mvc.token.SmsCodeAuthenticationToken;
import com.github.jgzl.common.core.constant.SecurityConstants;

/**
 * 自定义手机号验证码登录系统
 *
 * @author lihaifeng
 * 2019/7/12 14:13
 * @see UsernamePasswordAuthenticationFilter
 */
public class SmsCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final String SPRING_SECURITY_RESTFUL_PHONE_KEY = "mobile";
    private static final String SPRING_SECURITY_RESTFUL_VERIFY_CODE_KEY = "code";

    private boolean postOnly = true;

    public SmsCodeAuthenticationFilter() {
        super(new AntPathRequestMatcher(SecurityConstants.MOBILE_LOGIN_URL, HttpMethod.POST.name()));
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws
			AuthenticationException, IOException, ServletException {
        if (postOnly && !request.getMethod().equals(HttpMethod.POST.name())) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        String principal;
        String credentials;
        // 1. 从请求中获取参数 mobile + smsCode
        principal = obtainParameter(request, SPRING_SECURITY_RESTFUL_PHONE_KEY);
        credentials = obtainParameter(request, SPRING_SECURITY_RESTFUL_VERIFY_CODE_KEY);
        principal = principal.trim();
        SmsCodeAuthenticationToken authRequest = new SmsCodeAuthenticationToken(principal, credentials);
        this.setDetails(request, authRequest);
        // 3. 返回 authenticated 方法的返回值
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private String obtainParameter(HttpServletRequest request, String parameter) {
        String result =  request.getParameter(parameter);
        return result == null ? "" : result;
    }

    protected void setDetails(HttpServletRequest request, SmsCodeAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public boolean isPostOnly() {
        return postOnly;
    }
}
