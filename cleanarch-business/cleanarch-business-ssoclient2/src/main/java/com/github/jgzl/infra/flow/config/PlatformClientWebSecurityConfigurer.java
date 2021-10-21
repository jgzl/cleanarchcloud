package com.github.jgzl.infra.flow.config;

import com.github.jgzl.infra.flow.handler.PlatformLogoutSuccessHandler;
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
				.antMatchers(SecurityConstants.PATH_ACTUATOR, SecurityConstants.PATH_API_DoCS).permitAll()
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
