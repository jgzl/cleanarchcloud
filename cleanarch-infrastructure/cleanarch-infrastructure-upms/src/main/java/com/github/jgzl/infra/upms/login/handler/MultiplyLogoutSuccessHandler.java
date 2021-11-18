package com.github.jgzl.infra.upms.login.handler;

import cn.hutool.core.util.StrUtil;
import com.github.jgzl.common.core.util.SpringContextHolder;
import com.github.jgzl.infra.upms.controller.login.LogoutController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录成功处理
 * 访问 http://localhost:8010/logout?redirect_url=http://localhost:8010/heartbeat
 * 访问 http://localhost:8010/logout?access_token=xxxx&redirect_url=http://localhost:8010/heartbeat
 * 访问 http://localhost:8010/logout?redirect_url=http://www.baidu.com
 * 访问 http://localhost:8010/logout?access_token=xxxx&redirect_url=http://www.baidu.com
 * @author lihaifeng
 * 2019/7/5 17:27
 */
@Slf4j
@Component
public class MultiplyLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws IOException {
		ConsumerTokenServices consumerTokenServices = SpringContextHolder.getBean("consumerTokenServices");
		String access_token = request.getParameter("access_token");
		if (StrUtil.isNotBlank(access_token)) {
			if (consumerTokenServices.revokeToken(access_token)) {
				log.info("移除token成功");
			}else {
				log.info("移除token失败");
			}
		}
		// 获取请求参数中是否包含 回调地址
		LogoutController.transferToAnotherWebsite(request, response);
	}
}
