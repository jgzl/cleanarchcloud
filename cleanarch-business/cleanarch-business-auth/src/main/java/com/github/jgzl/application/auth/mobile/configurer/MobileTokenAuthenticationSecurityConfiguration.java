package com.github.jgzl.application.auth.mobile.configurer;

import com.github.jgzl.application.auth.mobile.filter.MobileTokenAuthenticationFilter;
import com.github.jgzl.application.auth.mobile.handler.MobileTokenLoginFailureHandler;
import com.github.jgzl.application.auth.mobile.handler.MobileTokenLoginSuccessHandler;
import com.github.jgzl.application.auth.mobile.provider.MobileTokenAuthenticationProvider;
import com.github.jgzl.common.data.redis.CustomRedisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.github.jgzl.application.auth.service.user.MobileUserDetailsService;

/**
 * 手机号/验证码获取Token 安全配置
 *
 * @author lihaifeng
 * 2019/7/22 16:14
 */
@Component
public class MobileTokenAuthenticationSecurityConfiguration extends
		SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    @Autowired
    private CustomRedisRepository redisRepository;

    @Autowired
    private MobileUserDetailsService mobileUserDetailsService;

    @Autowired
    private MobileTokenLoginFailureHandler mobileTokenLoginFailureHandler;

    @Autowired
    private MobileTokenLoginSuccessHandler mobileTokenLoginSuccessHandler;

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        final MobileTokenAuthenticationFilter filter = new MobileTokenAuthenticationFilter();
        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationSuccessHandler(mobileTokenLoginSuccessHandler);
        filter.setAuthenticationFailureHandler(mobileTokenLoginFailureHandler);

        final MobileTokenAuthenticationProvider provider = new MobileTokenAuthenticationProvider();
        provider.setRedisRepository(redisRepository);
        provider.setUserDetailsService(mobileUserDetailsService);
        provider.setHideUserNotFoundExceptions(false);

        http
                .authenticationProvider(provider)
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
