package com.gitee.application.auth.mvc.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gitee.common.core.config.SysProperties;
import com.gitee.common.core.util.Result;

/**
 * 用户名密码退出登录成功处理
 *
 * @author lihaifeng
 * 2019/7/5 17:27
 */
@Component
public class UsernamePasswordLogoutSuccessHandler implements LogoutSuccessHandler {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private SysProperties ssoProperties;

	@Override
	public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws IOException {
		response.setStatus(HttpServletResponse.SC_OK);
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.getWriter().write(objectMapper.writeValueAsString(Result.ok("登出成功")));
		response.sendRedirect(ssoProperties.getFrontendUrl());
	}
}