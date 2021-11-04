package com.github.jgzl.infra.upms.login.config.third;

import com.github.jgzl.infra.upms.login.filter.third.ThirdAuthenticationFilter;
import com.github.jgzl.infra.upms.login.handler.TenantSavedRequestAwareAuthenticationSuccessHandler;
import com.github.jgzl.infra.upms.login.handler.UsernamePasswordAuthenticationFailureHandler;
import com.github.jgzl.infra.upms.login.provider.third.ThirdLoginAuthenticationProvider;
import com.github.jgzl.infra.upms.service.impl.UsernameUserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 第三方登录 安全配置
 *
 * @author lihaifeng
 * 2019/7/22 16:14
 */
@AllArgsConstructor
@Configuration
public class ThirdAuthenticationSecurityConfiguration extends
		SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private UsernameUserDetailsServiceImpl userDetailsService;

    private TenantSavedRequestAwareAuthenticationSuccessHandler successHandler;

    private UsernamePasswordAuthenticationFailureHandler failureHandler;

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        final ThirdAuthenticationFilter filter = new ThirdAuthenticationFilter();
        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);

        final ThirdLoginAuthenticationProvider provider = new ThirdLoginAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setHideUserNotFoundExceptions(false);

        http
                .authenticationProvider(provider)
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
