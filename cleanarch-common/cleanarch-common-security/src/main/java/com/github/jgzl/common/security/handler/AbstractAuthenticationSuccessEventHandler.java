/*
 *    Copyright [2020] [lihaifeng,xuhang]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.jgzl.common.security.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import cn.hutool.core.collection.CollUtil;

/**
 * @author lihaifeng
 * @date 2018/10/8
 * 认证成功事件处理器
 */
public abstract class AbstractAuthenticationSuccessEventHandler implements
		ApplicationListener<AuthenticationSuccessEvent> {
	/**
	 * Handle an application event.
	 *
	 * @param event the event to respond to
	 */
	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes)
				RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = requestAttributes.getRequest();
		HttpServletResponse response = requestAttributes.getResponse();

		Authentication authentication = (Authentication) event.getSource();
		if (CollUtil.isNotEmpty(authentication.getAuthorities())) {
			handle(authentication, request, response);
		}
	}

	/**
	 * 处理登录成功方法
	 * <p>
	 * 获取到登录的authentication 对象
	 *
	 * @param authentication 登录对象
	 * @param request        请求
	 * @param response       响应
	 */
	public abstract void handle(Authentication authentication, HttpServletRequest request,
			HttpServletResponse response);
}
