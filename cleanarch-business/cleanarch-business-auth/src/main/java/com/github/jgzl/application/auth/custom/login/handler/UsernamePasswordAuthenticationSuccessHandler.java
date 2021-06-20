package com.github.jgzl.application.auth.custom.login.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jgzl.common.core.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
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

	@Autowired
	private ObjectMapper objectMapper;

	private RequestCache requestCache = new HttpSessionRequestCache();

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request,
			final HttpServletResponse response,
			final Authentication authentication) throws IOException, ServletException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		String redirectUri = null;
		if (savedRequest != null) {
			redirectUri = savedRequest.getRedirectUrl();
		}
		clearAuthenticationAttributes(request);
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(objectMapper.writeValueAsString(Result.ok(redirectUri)));
	}
}
