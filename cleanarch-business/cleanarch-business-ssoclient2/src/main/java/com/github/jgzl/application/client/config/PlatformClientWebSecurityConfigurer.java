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

package com.github.jgzl.application.client.config;

import com.github.jgzl.application.client.handler.PlatformLogoutSuccessHandler;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.github.jgzl.common.core.constant.SecurityConstants;

import lombok.SneakyThrows;

/**
 * @author lihaifeng
 * @date 2018/6/22
 * 认证相关配置
 */
@Order(101)
@Configuration
@EnableOAuth2Sso
public class PlatformClientWebSecurityConfigurer extends WebSecurityConfigurerAdapter {
	@Override
	@SneakyThrows
	protected void configure(HttpSecurity http) {
		http.logout().logoutSuccessHandler(logoutSuccessHandler())
				.and()
				.authorizeRequests()
				.antMatchers(SecurityConstants.PATH_ACTUATOR, SecurityConstants.PATH_API_DOCS).permitAll()
				.anyRequest().authenticated()
				.and()
				.csrf().disable();
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/bootstrap/**");
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new PlatformLogoutSuccessHandler();
	}
}
