package com.github.jgzl.application.client.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.github.jgzl.common.core.constant.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lihaifeng
 */
@Slf4j
public class PlatformLogoutSuccessHandler implements LogoutSuccessHandler {

	@Autowired
	private Environment environment;

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		log.info(environment.getProperty("spring.application.name") + ":登出成功");
		String profile;
		if (environment.getActiveProfiles().length > 0) {
			profile = environment.getActiveProfiles()[0];
		} else {
			profile = environment.getDefaultProfiles()[0];
		}
		if (SecurityConstants.DEV.equalsIgnoreCase(profile)
				|| SecurityConstants.LHF.equalsIgnoreCase(profile)
				|| SecurityConstants.XH.equalsIgnoreCase(profile)
		) {
			response.sendRedirect("http://localhost:8030/logout");
		}
	}
}
