package com.github.jgzl.infra.upms.login.config;
import com.github.jgzl.infra.upms.core.PathConstants;
import com.github.jgzl.infra.upms.login.config.email.EmailTokenAuthenticationSecurityConfiguration;
import com.github.jgzl.infra.upms.login.config.mobile.MobileAuthenticationSecurityConfiguration;
import com.github.jgzl.infra.upms.login.filter.username.MultiplyUsernamePasswordAuthenticationFilter;
import com.github.jgzl.infra.upms.login.handler.MultiplyLogoutSuccessHandler;
import com.github.jgzl.infra.upms.login.handler.TenantSavedRequestAwareAuthenticationSuccessHandler;
import com.github.jgzl.infra.upms.login.handler.UsernamePasswordAccessDeniedHandler;
import com.github.jgzl.infra.upms.login.handler.UsernamePasswordAuthenticationFailureHandler;
import com.github.jgzl.infra.upms.login.provider.third.ThirdLoginAuthenticationProvider;
import com.github.jgzl.infra.upms.service.impl.UserNameUserDetailsServiceImpl;
import com.github.jgzl.common.core.properties.FrameworkSecurityConfigProperties;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;

/**
 * webSecurity 权限控制类
 *
 * @author lihaifeng
 * @date 2018/7/24 15:58
 * @see SecurityFilterAutoConfiguration
 */
@Configuration
@AllArgsConstructor
public class CustomWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private DataSource dataSource;

	private FrameworkSecurityConfigProperties securityProperties;

    private UserNameUserDetailsServiceImpl userNameUserDetailsService;

    private TenantSavedRequestAwareAuthenticationSuccessHandler successHandler;

    private UsernamePasswordAuthenticationFailureHandler failureHandler;

    private UsernamePasswordAccessDeniedHandler accessDeniedHandler;

    private MultiplyLogoutSuccessHandler multiplyLogoutSuccessHandler;

    private EmailTokenAuthenticationSecurityConfiguration mobileTokenAuthenticationSecurityConfiguration;

    private MobileAuthenticationSecurityConfiguration mobileAuthenticationSecurityConfiguration;

	private PasswordEncoder passwordEncoder;

    private static final String RM_KEY = UUID.randomUUID().toString();

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry =
                http
                        // 默认的用户名密码认证器
                        .authenticationProvider(thirdLoginAuthenticationProvider())
                        .authenticationProvider(daoAuthenticationProvider())
                        .apply(mobileTokenAuthenticationSecurityConfiguration)
                        .and()
                        .apply(mobileAuthenticationSecurityConfiguration)
                        .and()
                        .addFilterAt(multiplyUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                        .formLogin().loginPage(PathConstants.LOGIN_PAGE_URL).loginProcessingUrl(PathConstants.LOGIN_URL)
                        .and()
						.logout()
						.logoutUrl(PathConstants.LOGOUT_URL)
						// logoutSuccessHandler有值时,logoutSuccessUrl不生效
						.logoutSuccessUrl(PathConstants.LOGOUT_SUCCESS_URL)
						.logoutSuccessHandler(multiplyLogoutSuccessHandler)
						.permitAll()
						.invalidateHttpSession(true)
                        // 异常处理filter: ExceptionTranslationFilter
                        .and()
						.exceptionHandling()
                        // 认证过的用户访问无权限资源时的异常
                        .accessDeniedHandler(accessDeniedHandler)
                        // 开启RememberMe
                        .and().rememberMe().key(RM_KEY).rememberMeServices(rememberMeServices())
                        .and().authorizeRequests();

		final List<String> urlPermitAll = securityProperties.getUrlPermitAll();
        urlPermitAll.forEach(url -> registry.antMatchers(url).permitAll());
        registry.anyRequest().authenticated().and().cors().and().csrf().disable();
    }

	/**
	 * 不拦截静态资源
	 * @param web
	 */
	@Override
	public void configure(WebSecurity web) {
		web.ignoring().antMatchers("/favicon.ico", "/css/**", "/error", "/doc/**");
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
	 * 第三方登录 AuthenticationProvider
	 */
	@Bean
	public ThirdLoginAuthenticationProvider thirdLoginAuthenticationProvider(){
		ThirdLoginAuthenticationProvider thirdLoginAuthenticationProvider = new ThirdLoginAuthenticationProvider();
		thirdLoginAuthenticationProvider.setUserDetailsService(userNameUserDetailsService);
		return thirdLoginAuthenticationProvider;
	}

    /**
     * 注册自定义的UsernamePasswordAuthenticationFilter
     *
     * @throws Exception
     */
    @Bean
    public MultiplyUsernamePasswordAuthenticationFilter multiplyUsernamePasswordAuthenticationFilter() throws Exception {
		MultiplyUsernamePasswordAuthenticationFilter filter = new MultiplyUsernamePasswordAuthenticationFilter();
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
