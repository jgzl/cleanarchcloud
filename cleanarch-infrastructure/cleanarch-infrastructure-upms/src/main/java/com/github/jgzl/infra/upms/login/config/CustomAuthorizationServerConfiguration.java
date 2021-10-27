package com.github.jgzl.infra.upms.login.config;

import com.github.jgzl.common.core.constant.SecurityConstants;
import com.github.jgzl.infra.upms.core.PathConstants;
import com.github.jgzl.infra.upms.login.exception.CustomWebResponseExceptionTranslator;
import com.github.jgzl.infra.upms.login.handler.UsernamePasswordAccessDeniedHandler;
import com.github.jgzl.infra.upms.login.handler.UsernamePasswordExceptionEntryPoint;
import com.github.jgzl.infra.upms.login.service.CustomClientDetailsService;
import com.github.jgzl.infra.upms.login.service.RedisAuthenticationCodeServices;
import com.github.jgzl.infra.upms.service.impl.UserNameUserDetailsServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import javax.sql.DataSource;

/**
 * 认证服务器配置抽象
 *
 * @author lihaifeng
 * @date 2018/7/24 16:10
 */
@AllArgsConstructor
@Configuration
@EnableAuthorizationServer
public class CustomAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

    private final UserNameUserDetailsServiceImpl userNameUserDetailsService;

    private final CustomWebResponseExceptionTranslator exceptionTranslator;

    private final UsernamePasswordExceptionEntryPoint exceptionEntryPoint;

    private final UsernamePasswordAccessDeniedHandler accessDeniedHandler;

	private final DataSource dataSource;

	private final AuthorizationServerTokenServices authorizationServerTokenServices;

	private final RedisAuthenticationCodeServices redisAuthenticationCodeServices;

	@Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    	CustomClientDetailsService clientDetailsService=new CustomClientDetailsService(dataSource);
    	clientDetailsService.setSelectClientDetailsSql(SecurityConstants.DEFAULT_SELECT_STATEMENT);
    	clientDetailsService.setFindClientDetailsSql(SecurityConstants.DEFAULT_FIND_STATEMENT);
        clients.withClientDetails(clientDetailsService);
    }

    /**
     * 授权服务器端点配置，如令牌存储，令牌自定义，用户批准和授权类型，不包括端点安全配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                .authenticationManager(authenticationManager)
                .userDetailsService(userNameUserDetailsService)
                .tokenServices(authorizationServerTokenServices)
                .exceptionTranslator(exceptionTranslator)
				.pathMapping("/oauth/confirm_access", PathConstants.LOGIN_CONFIRM_URL)
                .authorizationCodeServices(redisAuthenticationCodeServices);
    }

    /**
     * 授权服务器端点的安全配置
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
				//isAuthenticated() permitAll()
				.tokenKeyAccess("permitAll()")
				.checkTokenAccess("isAuthenticated()")
                //allowFormAuthenticationForClients是为了注册clientCredentialsTokenEndpointFilter
                //clientCredentialsTokenEndpointFilter,解析request中的client_id和client_secret
                //构造成UsernamePasswordAuthenticationToken,然后通过UserDetailsService查询作简单的认证而已
                //一般是针对password模式和client_credentials 主要是让/oauth/token支持client_id以及client_secret作登录认证
                .allowFormAuthenticationForClients()
                .authenticationEntryPoint(exceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }

}
