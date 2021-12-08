package com.github.jgzl.common.security.mobile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jgzl.common.security.component.ExtendCommenceAuthExceptionEntryPoint;
import com.github.jgzl.common.security.service.ExtendUserDetailsService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author lihaifeng
 * @date 2018/8/5 手机号登录配置入口
 */
@Getter
@Setter
public class MobileSecurityConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AuthenticationSuccessHandler mobileLoginSuccessHandler;

	@Autowired
	private ExtendUserDetailsService userDetailsService;

	@Override
	public void configure(HttpSecurity http) {
		MobileAuthenticationFilter mobileAuthenticationFilter = new MobileAuthenticationFilter();
		mobileAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
		mobileAuthenticationFilter.setAuthenticationSuccessHandler(mobileLoginSuccessHandler);
		mobileAuthenticationFilter.setAuthenticationEntryPoint(new ExtendCommenceAuthExceptionEntryPoint(objectMapper));

		MobileAuthenticationProvider mobileAuthenticationProvider = new MobileAuthenticationProvider();
		mobileAuthenticationProvider.setUserDetailsService(userDetailsService);
		http.authenticationProvider(mobileAuthenticationProvider).addFilterAfter(mobileAuthenticationFilter,
				UsernamePasswordAuthenticationFilter.class);
	}

}
