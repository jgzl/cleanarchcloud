package com.github.jgzl.infra.upms.login.config.mobile;
import com.github.jgzl.infra.upms.login.handler.TenantSavedRequestAwareAuthenticationSuccessHandler;
import com.github.jgzl.infra.upms.login.handler.UsernamePasswordAuthenticationFailureHandler;
import com.github.jgzl.infra.upms.login.filter.mobile.MobileAuthenticationFilter;
import com.github.jgzl.infra.upms.login.provider.mobile.MobileAuthenticationProvider;
import com.github.jgzl.infra.upms.service.impl.MobileUserDetailsService;
import com.github.jgzl.common.cache.support.RedisRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
/**
 * 手机号/验证码登录 安全配置
 *
 * @author lihaifeng
 * 2019/7/22 16:14
 */
@AllArgsConstructor
@Configuration
public class MobileAuthenticationSecurityConfiguration extends
		SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private RedisRepository redisRepository;
    private MobileUserDetailsService userDetailsService;
    private TenantSavedRequestAwareAuthenticationSuccessHandler successHandler;
    private UsernamePasswordAuthenticationFailureHandler failureHandler;
    @Override
    public void configure(final HttpSecurity http) throws Exception {
        final MobileAuthenticationFilter filter = new MobileAuthenticationFilter();
        filter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        final MobileAuthenticationProvider provider = new MobileAuthenticationProvider();
        provider.setRedisRepository(redisRepository);
        provider.setUserDetailsService(userDetailsService);
        provider.setHideUserNotFoundExceptions(false);
        http
                .authenticationProvider(provider)
                .addFilterAfter(filter, UsernamePasswordAuthenticationFilter.class);
    }
}
