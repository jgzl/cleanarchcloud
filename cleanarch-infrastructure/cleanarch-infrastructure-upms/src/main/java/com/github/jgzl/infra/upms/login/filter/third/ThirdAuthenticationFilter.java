package com.github.jgzl.infra.upms.login.filter.third;

import cn.hutool.json.JSONUtil;
import com.github.jgzl.infra.upms.core.PathConstants;
import com.github.jgzl.infra.upms.login.token.third.ThirdLoginAuthenticationToken;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.enums.AuthResponseStatus;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
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
 *  <img src="https://justauth.wiki/_media/extended/justauth_integrated_with_the_existing_account_system.png"/>
 *
 * @author lihaifeng
 * 2019/7/12 14:13
 * @see UsernamePasswordAuthenticationFilter
 */
@Slf4j
public class ThirdAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private AuthRequestFactory authRequestFactory;

	public ThirdAuthenticationFilter() {
		// 定义一个指定路径的手机验证码登录前缀
        super(new AntPathRequestMatcher(PathConstants.LOGIN_THIRD_CALLBACK_URL, null));
    }

    @Override
    public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response) throws
			AuthenticationException {
		AuthUser authUser = obtainAuthUser(request);
        ThirdLoginAuthenticationToken authRequest = new ThirdLoginAuthenticationToken(authUser, null);
        this.setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

	/**
	 * 获取 justauth 登录后的用户信息
	 */
	protected AuthUser obtainAuthUser(HttpServletRequest request) {
		String type = getCallbackType(request);
		AuthRequest authRequest = authRequestFactory.get(type);
		// 登录后，从第三方拿到用户信息
		AuthResponse response = authRequest.login(getCallback(request));
		log.info("【justauth 第三方登录 response】= {}", JSONUtil.toJsonStr(response));
		// 第三方登录成功
		if (response.getCode() == AuthResponseStatus.SUCCESS.getCode()) {
			AuthUser authUser = (AuthUser) response.getData();
			return authUser;
		}
		return null;
	}

	/**
	 * 从请求中构建 AuthCallback
	 */
	private AuthCallback getCallback(HttpServletRequest request) {
		AuthCallback authCallback = AuthCallback.builder()
				.code(request.getParameter("code"))
				.auth_code(request.getParameter("auth_code"))
				.authorization_code(request.getParameter("authorization_code"))
				.oauth_token(request.getParameter("oauth_token"))
				.state(request.getParameter("state"))
				.oauth_verifier(request.getParameter("oauth_verifier"))
				.build();
		return authCallback;
	}

	/**
	 * 获取路径参数：回调类型
	 */
	private String getCallbackType(HttpServletRequest request) {
		String uri = request.getRequestURI();
		int end = uri.length() - PathConstants.LOGIN_THIRD_CALLBACK.length();
		int start = PathConstants.LOGIN_MODULE_THIRD_PATH.length();
		if(start == end) {
			log.warn("【justauth 第三方登录 response】回调类型为空，uri={}", uri);
			return null;
		}
		return uri.substring(start,end);
	}

    protected void setDetails(HttpServletRequest request, ThirdLoginAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

	public void setAuthRequestFactory(AuthRequestFactory authRequestFactory) {
		this.authRequestFactory = authRequestFactory;
	}

	public AuthRequestFactory getAuthRequestFactory() {
		return authRequestFactory;
	}
}
