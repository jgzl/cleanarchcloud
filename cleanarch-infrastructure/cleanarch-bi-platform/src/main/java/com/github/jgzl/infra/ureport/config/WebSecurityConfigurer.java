
package com.github.jgzl.infra.ureport.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * WebSecurityConfigurer
 *
 * @author lihaifeng
 * @date 2020-12-05
 */
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Override
	@SneakyThrows
	protected void configure(HttpSecurity http) {
		http.headers().frameOptions().disable().and().formLogin().loginPage("/login.html").loginProcessingUrl("/login")
				.failureHandler(new LoginFailureHandler()).and().authorizeRequests()
				.antMatchers("/login.html", "/login", "/actuator/**").permitAll().anyRequest().authenticated().and()
				.csrf().disable();
	}

	/**
	 * 不拦截静态资源
	 * @param web
	 */
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/favicon.ico", "/css/**", "/error");
	}

	/**
	 * 登录失败处理器，携带错误信息重定向到首页
	 */
	static class LoginFailureHandler implements AuthenticationFailureHandler {

		/**
		 * Called when an authentication attempt fails.
		 * @param request the request during which the authentication attempt occurred.
		 * @param response the response.
		 * @param exception the exception which was thrown to reject the authentication
		 */
		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException {
			response.sendRedirect("/login.html?error="
					+ URLEncoder.encode(exception.getLocalizedMessage(), StandardCharsets.UTF_8.name()));
		}

	}

}
