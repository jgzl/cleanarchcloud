package com.github.jgzl.infra.upms.custom.login.handler;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.jgzl.common.core.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 用户名密码退出登录成功处理
 *
 * @author lihaifeng
 * 2019/7/5 17:27
 */
@Slf4j
@Component
public class UsernamePasswordLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws IOException {
		String callBackUrl = request.getParameter("callBackUrl");
		if (StrUtil.isBlank(callBackUrl)) {
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType(MediaType.APPLICATION_JSON_VALUE);
			response.setCharacterEncoding(StandardCharsets.UTF_8.name());
			response.getWriter().write(JSONUtil.toJsonStr(Result.failed("callBackUrl不允许为空,否则无法重定向!")));
		}else {
			response.sendRedirect(callBackUrl);
		}
	}
}
