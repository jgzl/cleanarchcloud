package com.gitee.application.auth.mvc.configurer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.gitee.application.auth.mvc.filter.SmsCodeAuthenticationFilter;
import com.gitee.application.auth.mvc.handler.UsernamePasswordAuthenticationFailureHandler;
import com.gitee.application.auth.mvc.handler.UsernamePasswordAuthenticationSuccessHandler;
import com.gitee.application.auth.mvc.provider.SmsCodeAuthenticationProvider;
import com.gitee.application.auth.service.user.MobileUserDetailsService;
import com.gitee.common.data.redis.CustomRedisRepository;

/**
 * 手机号/验证码登录 安全配置
 *
 * @author lihaifeng
 * 2019/7/22 16:14
 */
@Component
public class SmsCodeAuthenticationSecurityConfigration extends
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
