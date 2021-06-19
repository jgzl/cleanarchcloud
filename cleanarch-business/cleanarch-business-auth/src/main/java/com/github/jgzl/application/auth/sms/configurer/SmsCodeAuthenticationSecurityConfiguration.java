package com.github.jgzl.application.auth.sms.configurer;

import com.github.jgzl.application.auth.normal.handler.*;
import com.github.jgzl.application.auth.service.user.MobileUserDetailsService;
import com.github.jgzl.application.auth.sms.filter.SmsCodeAuthenticationFilter;
import com.github.jgzl.application.auth.sms.provider.SmsCodeAuthenticationProvider;
import com.github.jgzl.common.data.redis.CustomRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

/**
 * 手机号/验证码登录 安全配置
 *
 * @author lihaifeng
 * 2019/7/22 16:14
 */
@Component
public class SmsCodeAuthenticationSecurityConfiguration extends
		SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private CustomRedisRepository redisRepository;

    @Autowired
    private MobileUserDetailsService mobileUserDetailsService;

    @Autowired
    private UsernamePasswordAuthenticationSuccessHandler successHandler;

    @Autowired
    private UsernamePasswordAuthenticationFailureHandler failureHandler;

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        final SmsCodeAuthenticationFilter filter = new SmsCodeAuthenticationFilter();
        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);

        final SmsCodeAuthenticationProvider provider = new SmsCodeAuthenticationProvider();
        provider.setRedisRepository(redisRepository);
        provider.setUserDetailsService(mobileUserDetailsService);
        provider.setHideUserNotFoundExceptions(false);

        http
                .authenticationProvider(provider)
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
