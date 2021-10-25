package com.github.jgzl.infra.upms.login.normal.config;
import com.github.jgzl.infra.upms.core.PathConstants;
import com.github.jgzl.infra.upms.login.normal.config.email.EmailTokenAuthenticationSecurityConfiguration;
import com.github.jgzl.infra.upms.login.normal.config.mobile.MobileAuthenticationSecurityConfiguration;
import com.github.jgzl.infra.upms.login.normal.filter.username.UsernamePasswordAuthenticationFilter;
import com.github.jgzl.infra.upms.login.normal.handler.UsernamePasswordAccessDeniedHandler;
import com.github.jgzl.infra.upms.login.normal.handler.UsernamePasswordAuthenticationFailureHandler;
import com.github.jgzl.infra.upms.login.normal.handler.UsernamePasswordAuthenticationSuccessHandler;
import com.github.jgzl.infra.upms.login.normal.handler.UsernamePasswordLogoutSuccessHandler;
import com.github.jgzl.infra.upms.service.impl.UserNameUserDetailsServiceImpl;
import com.github.jgzl.common.core.properties.SecurityConfigProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

/**
 * webSecurity 权限控制类
 *
 * @author lihaifeng
 * @date 2018/7/24 15:58
 * @see SecurityFilterAutoConfiguration
 */
@AllArgsConstructor
@EnableWebSecurity
public class CustomWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;

	private SecurityConfigProperties securityProperties;

    private UserNameUserDetailsServiceImpl userNameUserDetailsService;

    private UsernamePasswordAuthenticationSuccessHandler successHandler;

    private UsernamePasswordAuthenticationFailureHandler failureHandler;

    private UsernamePasswordAccessDeniedHandler accessDeniedHandler;

    private UsernamePasswordLogoutSuccessHandler logoutSuccessHandler;

    private EmailTokenAuthenticationSecurityConfiguration mobileTokenAuthenticationSecurityConfiguration;

    private MobileAuthenticationSecurityConfiguration mobileAuthenticationSecurityConfiguration;

	private PasswordEncoder passwordEncoder;

    private static final String RM_KEY = UUID.randomUUID().toString();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry =
                http
                        // 默认的用户名密码认证器
                        .authenticationProvider(daoAuthenticationProvider())
                        .apply(mobileTokenAuthenticationSecurityConfiguration)
                        .and()
                        .apply(mobileAuthenticationSecurityConfiguration)
                        .and()
                        .addFilterAt(customAuthenticationFilter(), org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter.class)
                        .formLogin().loginPage(PathConstants.LOGIN_PAGE_URL)
                        .loginProcessingUrl(PathConstants.LOGIN_URL)
                        .and().logout().logoutUrl("/logout").permitAll().logoutSuccessHandler(logoutSuccessHandler)
                        // 异常处理filter: ExceptionTranslationFilter
                        .and().exceptionHandling()
                        // 认证过的用户访问无权限资源时的异常
                        .accessDeniedHandler(accessDeniedHandler)
                        // 开启RememberMe
                        .and().rememberMe().key(RM_KEY).rememberMeServices(rememberMeServices())
                        .and().authorizeRequests();

		final List<String> urlPermitAll = securityProperties.getUrlPermitAll();
        urlPermitAll.forEach(url -> registry.antMatchers(url).permitAll());
        registry.anyRequest().authenticated().and().cors().and().csrf().disable();
    }

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/static/**","/public/**");
	}

    /**
     * 这一步的配置是必不可少的，否则SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 默认的用户名密码 AuthenticationProvider
     *
     * @return
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userNameUserDetailsService);
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    /**
     * 注册自定义的UsernamePasswordAuthenticationFilter
     *
     * @throws Exception
     */
    @Bean
    public UsernamePasswordAuthenticationFilter customAuthenticationFilter() throws Exception {
		UsernamePasswordAuthenticationFilter filter = new UsernamePasswordAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        filter.setRememberMeServices(rememberMeServices());
        //这句很关键，重用WebSecurityConfigurerAdapter配置的AuthenticationManager，不然要自己组装AuthenticationManager
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }

    /**
     * RememberMeServices
     */
    public RememberMeServices rememberMeServices() {
        final JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return new PersistentTokenBasedRememberMeServices(RM_KEY, userNameUserDetailsService, tokenRepository);
    }
}
