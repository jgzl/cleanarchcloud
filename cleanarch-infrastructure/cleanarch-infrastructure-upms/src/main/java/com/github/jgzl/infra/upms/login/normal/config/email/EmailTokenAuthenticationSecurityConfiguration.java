package com.github.jgzl.infra.upms.login.normal.config.email;
import com.github.jgzl.infra.upms.login.normal.filter.email.EmailTokenAuthenticationFilter;
import com.github.jgzl.infra.upms.login.normal.handler.UsernamePasswordAuthenticationFailureHandler;
import com.github.jgzl.infra.upms.login.normal.handler.UsernamePasswordAuthenticationSuccessHandler;
import com.github.jgzl.infra.upms.login.normal.provider.email.EmailTokenAuthenticationProvider;
import com.github.jgzl.infra.upms.service.impl.EmailUserDetailsService;
import com.github.jgzl.common.cache.support.CustomRedisRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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
@AllArgsConstructor
@Configuration
public class EmailTokenAuthenticationSecurityConfiguration extends
		SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private CustomRedisRepository redisRepository;

    private EmailUserDetailsService userDetailsService;

	private UsernamePasswordAuthenticationSuccessHandler successHandler;

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
