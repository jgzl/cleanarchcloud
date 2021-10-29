package com.github.jgzl.application.handler;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
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
		String redirectUrl = environment.getProperty("cleanarch.security.redirect-url");
		if (StrUtil.isBlank(redirectUrl)) {
			redirectUrl = "/";
		}
		if (authentication!=null) {
			Object details = authentication.getDetails();
			if (details!=null) {
				JSONObject jsonObject = JSONUtil.parseObj(details);
				Object tokenValue = jsonObject.get("tokenValue");
				if (tokenValue!=null) {
					redirectUrl = redirectUrl+"&access_token="+tokenValue;
				}
			}
		}
		log.info(environment.getProperty("spring.application.name") + ":登出成功");
		log.info("跳转地址:"+ redirectUrl);
		response.sendRedirect(redirectUrl);
	}
}
