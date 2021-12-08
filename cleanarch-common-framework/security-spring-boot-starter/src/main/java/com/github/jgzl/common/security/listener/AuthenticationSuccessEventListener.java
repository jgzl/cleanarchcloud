package com.github.jgzl.common.security.listener;

import cn.hutool.core.collection.CollUtil;
import com.github.jgzl.common.security.handler.AuthenticationSuccessHandler;
import com.github.jgzl.common.security.service.ExtendUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lihaifeng
 * @date 2020/03/25 认证成功事件监听器
 */
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

	@Autowired(required = false)
	private AuthenticationSuccessHandler successHandler;

	/**
	 * Handle an application event.
	 * @param event the event to respond to
	 */
	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		Authentication authentication = (Authentication) event.getSource();
		if (successHandler != null && isUserAuthentication(authentication)) {
			ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
					.getRequestAttributes();
			HttpServletRequest request = requestAttributes.getRequest();
			HttpServletResponse response = requestAttributes.getResponse();

			successHandler.handle(authentication, request, response);
		}
	}

	private boolean isUserAuthentication(Authentication authentication) {
		return authentication.getPrincipal() instanceof ExtendUser
				|| CollUtil.isNotEmpty(authentication.getAuthorities());
	}

}
