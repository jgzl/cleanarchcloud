package com.github.jgzl.infra.upms.custom.login.filter.username;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jgzl.infra.upms.core.PathConstants;
import com.github.jgzl.common.security.vo.AuthenticationBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

/**
 * 重写 UsernamePasswordAuthenticationFilter 类, 支持实现异步JSON登录
 *
 * @author lihaifeng
 * 2019/7/3 14:09
 * @see org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
 */
@Slf4j
public class UsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";
	public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
	private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher(PathConstants.LOGIN_URL, "POST");
	private String usernameParameter = "username";
	private String passwordParameter = "password";
	private boolean postOnly = true;

	public UsernamePasswordAuthenticationFilter() {
		super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
	}

	public UsernamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
		super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
	}

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
        AuthenticationException {

        // 如果是JSON登录
        UsernamePasswordAuthenticationToken authRequest;
        if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {
            ObjectMapper mapper = new ObjectMapper();

            try (InputStream is = request.getInputStream()) {
                AuthenticationBean authenticationBean = mapper.readValue(is, AuthenticationBean.class);
                authRequest = new UsernamePasswordAuthenticationToken(
                        authenticationBean.getUsername(), authenticationBean.getPassword());
                this.setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            } catch (IOException e) {
                log.error("异步登陆失败", e);
            }
        } else {
            // 支持原来默认的登录方式
			if (this.postOnly && !request.getMethod().equals("POST")) {
				throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
			} else {
				String username = this.obtainUsername(request);
				username = username != null ? username : "";
				username = username.trim();
				String password = this.obtainPassword(request);
				password = password != null ? password : "";
				authRequest = new UsernamePasswordAuthenticationToken(username, password);
				this.setDetails(request, authRequest);
				return this.getAuthenticationManager().authenticate(authRequest);
			}
        }
        authRequest = new UsernamePasswordAuthenticationToken("", "");
        return this.getAuthenticationManager().authenticate(authRequest);
    }

	@Nullable
	protected String obtainPassword(HttpServletRequest request) {
		return request.getParameter(this.passwordParameter);
	}

	@Nullable
	protected String obtainUsername(HttpServletRequest request) {
		return request.getParameter(this.usernameParameter);
	}

	protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {
		authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
	}

	public void setUsernameParameter(String usernameParameter) {
		Assert.hasText(usernameParameter, "Username parameter must not be empty or null");
		this.usernameParameter = usernameParameter;
	}

	public void setPasswordParameter(String passwordParameter) {
		Assert.hasText(passwordParameter, "Password parameter must not be empty or null");
		this.passwordParameter = passwordParameter;
	}

	public void setPostOnly(boolean postOnly) {
		this.postOnly = postOnly;
	}

	public final String getUsernameParameter() {
		return this.usernameParameter;
	}

	public final String getPasswordParameter() {
		return this.passwordParameter;
	}

}
