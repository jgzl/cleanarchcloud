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

package com.gitee.application.auth.component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.gitee.common.security.handler.AbstractAuthenticationFailureEvenHandler;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lihaifeng
 * @date 2018/10/8
 */
@Slf4j
@Component
public class PlatformAuthenticationFailureEvenHandler extends AbstractAuthenticationFailureEvenHandler {

	/**
	 * 处理登录失败方法
	 * <p>
	 *
	 * @param authenticationException 登录的authentication 对象
	 * @param authentication          登录的authenticationException 对象
	 * @param request                 请求
	 * @param response                响应
	 */
	@Override
	public void handle(AuthenticationException authenticationException, Authentication authentication,
			HttpServletRequest request, HttpServletResponse response) {
		log.info("用户：{} 登录失败，异常：{}", authentication.getPrincipal(), authenticationException.getLocalizedMessage());
	}
}
