package com.github.jgzl.application.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import com.github.jgzl.common.core.constant.SecurityConstants;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lihaifeng
 */
@Slf4j
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {

	@Autowired
	private Environment environment;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String frontendUrl = environment.getProperty("cleanarch.frontend-url");
		if (StrUtil.isBlank(frontendUrl)) {
			frontendUrl = "/";
		}
		log.info(environment.getProperty("spring.application.name") + ":登出成功");
		log.info("跳转地址:"+ frontendUrl);
		response.sendRedirect(frontendUrl);
	}
}
