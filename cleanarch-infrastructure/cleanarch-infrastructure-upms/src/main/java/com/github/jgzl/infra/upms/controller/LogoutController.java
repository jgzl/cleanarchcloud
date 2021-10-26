package com.github.jgzl.infra.upms.controller;

import cn.hutool.core.util.StrUtil;
import com.github.jgzl.common.core.constant.SecurityConstants;
import com.github.jgzl.infra.upms.core.PathConstants;
import lombok.AllArgsConstructor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登出控制器
 */
@Slf4j
@AllArgsConstructor
@Controller
public class LogoutController {

	ConsumerTokenServices consumerTokenServices;

	/**
	 * 退出登录(普通方式退出和oauth2方式退出),使用oauth2方式退出必须增加access_token参数
	 * @param request
	 * @param response
	 * @param access_token 授权token
	 */
	@SneakyThrows
	@RequestMapping(value = PathConstants.LOGOUT_SUCCESS_URL,method = {RequestMethod.GET,RequestMethod.POST,RequestMethod.PUT,RequestMethod.DELETE})
	public void revokeToken(HttpServletRequest request, HttpServletResponse response, @RequestParam(required = false) String access_token) {
		new SecurityContextLogoutHandler().logout(request, null, null);
		if (StrUtil.isNotBlank(access_token)) {
			if (consumerTokenServices.revokeToken(request.getParameter("access_token"))) {
				log.info("移除token成功");
			}else {
				log.info("移除token失败");
			}
		}
		// 获取请求参数中是否包含 回调地址
		transferToAnotherWebsite(request, response);
	}

	public static void transferToAnotherWebsite(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String redirectUrl = request.getParameter(SecurityConstants.REDIRECT_URL);
		String referer = request.getHeader(HttpHeaders.REFERER);

		if (StrUtil.isNotBlank(redirectUrl)) {
			response.sendRedirect(redirectUrl);
		}else if (StrUtil.isNotBlank(referer)) {
			// 默认跳转referer 地址
			response.sendRedirect(referer);
		}else {
			response.sendRedirect("/");
		}
	}

}
