package com.github.jgzl.infra.upms.login.filter.third;

import com.github.jgzl.infra.upms.core.PathConstants;
import com.github.jgzl.infra.upms.login.token.third.ThirdLoginAuthenticationToken;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 第三方登录-登录系统
 *
 * @author lihaifeng
 * 2019/7/12 14:13
 * @see UsernamePasswordAuthenticationFilter
 */
public class ThirdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";

    public ThirdAuthenticationFilter() {
		// 定义一个指定路径的手机验证码登录前缀
        super(new AntPathRequestMatcher(PathConstants.LOGIN_MODULE_THIRD_PATH, HttpMethod.GET.name()));
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws
			AuthenticationException {
        String principal;
        String credentials;
        // 1. 从请求中获取参数 username + password
        principal = obtainParameter(request, SPRING_SECURITY_FORM_USERNAME_KEY);
        credentials = obtainParameter(request, SPRING_SECURITY_FORM_PASSWORD_KEY);
        principal = principal.trim();
        ThirdLoginAuthenticationToken authRequest = new ThirdLoginAuthenticationToken(principal, credentials);
        this.setDetails(request, authRequest);
        // 3. 返回 authenticated 方法的返回值
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private String obtainParameter(HttpServletRequest request, String parameter) {
        String result =  request.getParameter(parameter);
        return result == null ? "" : result;
    }

    protected void setDetails(HttpServletRequest request, ThirdLoginAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }
}
