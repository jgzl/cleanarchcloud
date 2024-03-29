package com.github.jgzl.application.config;

import com.github.jgzl.application.handler.CustomLogoutSuccessHandler;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @author lihaifeng
 * @date 2020/6/22
 * 认证相关配置
 */
@Order(101)
@Configuration
@EnableOAuth2Sso
public class ClientWebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Value("${server.servlet.session.cookie.comment:cookie}")
	private String cookieName;

	@Override
	@SneakyThrows
	protected void configure(HttpSecurity http) {
		http.logout().logoutSuccessHandler(logoutSuccessHandler()).deleteCookies(cookieName)
				.and()
				.authorizeRequests()
				.antMatchers("/actuator/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.csrf().disable();
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new CustomLogoutSuccessHandler();
	}
}