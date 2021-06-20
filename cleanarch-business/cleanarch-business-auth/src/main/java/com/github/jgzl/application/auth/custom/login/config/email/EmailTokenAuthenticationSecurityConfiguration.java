package com.github.jgzl.application.auth.custom.login.config.email;

import com.github.jgzl.application.auth.custom.login.filter.email.EmailTokenAuthenticationFilter;
import com.github.jgzl.application.auth.custom.login.handler.UsernamePasswordAuthenticationFailureHandler;
import com.github.jgzl.application.auth.custom.login.handler.UsernamePasswordAuthenticationSuccessHandler;
import com.github.jgzl.application.auth.custom.login.provider.email.EmailTokenAuthenticationProvider;
import com.github.jgzl.application.auth.service.user.EmailUserDetailsService;
import com.github.jgzl.common.data.redis.CustomRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 手机号/验证码获取Token 安全配置
 *
 * @author lihaifeng
 * 2019/7/22 16:14
 */
@Component
public class EmailTokenAuthenticationSecurityConfiguration extends
		SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private CustomRedisRepository redisRepository;

    @Autowired
    private EmailUserDetailsService userDetailsService;

	@Autowired
	private UsernamePasswordAuthenticationSuccessHandler successHandler;

	@Autowired
	private UsernamePasswordAuthenticationFailureHandler failureHandler;

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        final EmailTokenAuthenticationFilter filter = new EmailTokenAuthenticationFilter();
        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);

        final EmailTokenAuthenticationProvider provider = new EmailTokenAuthenticationProvider();
        provider.setRedisRepository(redisRepository);
        provider.setUserDetailsService(userDetailsService);
        provider.setHideUserNotFoundExceptions(false);

        http
                .authenticationProvider(provider)
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
