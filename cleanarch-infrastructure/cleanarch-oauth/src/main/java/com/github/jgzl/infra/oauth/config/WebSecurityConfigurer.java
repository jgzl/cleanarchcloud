package com.github.jgzl.infra.oauth.config;

import com.github.jgzl.common.security.handler.FormAuthenticationFailureHandler;
import com.github.jgzl.common.security.handler.MobileLoginSuccessHandler;
import com.github.jgzl.common.security.handler.SsoLogoutSuccessHandler;
import com.github.jgzl.common.security.handler.TenantSavedRequestAwareAuthenticationSuccessHandler;
import com.github.jgzl.common.security.mobile.MobileSecurityConfigurer;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * @author lihaifeng
 * @date 2018/6/22 认证相关配置
 */
@Primary
@Order(90)
@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Override
	@SneakyThrows
	protected void configure(HttpSecurity http) {
		http.formLogin().loginPage("/token/login").loginProcessingUrl("/token/form")
				.successHandler(tenantSavedRequestAwareAuthenticationSuccessHandler())
				.failureHandler(authenticationFailureHandler()).and().logout()
				.logoutSuccessHandler(logoutSuccessHandler()).deleteCookies("JSESSIONID").invalidateHttpSession(true)
				.and().authorizeRequests().antMatchers("/heartbeat","/token/**", "/actuator/**", "/mobile/**").permitAll()
				.anyRequest().authenticated().and().csrf().disable().apply(mobileSecurityConfigurer());
	}

	/**
	 * 不拦截静态资源
	 * @param web
	 */
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/favicon.ico", "/css/**", "/error");
	}

	@Bean
	@Override
	@SneakyThrows
	public AuthenticationManager authenticationManagerBean() {
		return super.authenticationManagerBean();
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler() {
		return new FormAuthenticationFailureHandler();
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new SsoLogoutSuccessHandler();
	}

	@Bean
	public AuthenticationSuccessHandler mobileLoginSuccessHandler() {
		return new MobileLoginSuccessHandler();
	}

	/**
	 * 具备租户传递能力
	 * @return AuthenticationSuccessHandler
	 */
	@Bean
	public AuthenticationSuccessHandler tenantSavedRequestAwareAuthenticationSuccessHandler() {
		return new TenantSavedRequestAwareAuthenticationSuccessHandler();
	}

	@Bean
	public MobileSecurityConfigurer mobileSecurityConfigurer() {
		return new MobileSecurityConfigurer();
	}

	/**
	 * https://spring.io/blog/2017/11/01/spring-security-5-0-0-rc1-released#password-storage-updated
	 * Encoded password does not look like BCrypt
	 * @return PasswordEncoder
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
