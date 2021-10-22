package com.github.jgzl.infra.upms.custom.login.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户名密码登录成功返回
 *
 * @author lihaifeng
 * 2019/7/3 14:30
 * @see SavedRequestAwareAuthenticationSuccessHandler
 */
@Slf4j
@Component
public class UsernamePasswordAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private static final RequestCache requestCache = new HttpSessionRequestCache();

	private static final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request,
			final HttpServletResponse response,
			final Authentication authentication) throws IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		String redirectUri = null;
		if (savedRequest != null) {
			redirectUri = savedRequest.getRedirectUrl();
		}
		log.info("登录成功,重定向url为{}",redirectUri);
		clearAuthenticationAttributes(request);
		redirectStrategy.sendRedirect(request,response,redirectUri);
	}
}
