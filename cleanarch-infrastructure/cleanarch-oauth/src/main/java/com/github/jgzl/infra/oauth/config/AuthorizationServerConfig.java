package com.github.jgzl.infra.oauth.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.jgzl.common.security.component.ExtendCommenceAuthExceptionEntryPoint;
import com.github.jgzl.common.security.component.ExtendWebResponseExceptionTranslator;
import com.github.jgzl.common.security.service.ExtendCustomTokenServices;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import java.util.Collections;

/**
 * @author lihaifeng
 * @date 2018/6/22 认证服务器配置
 */
@Configuration
@AllArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

	private final ClientDetailsService clientDetailsServiceImpl;

	private final AuthenticationManager authenticationManagerBean;

	private final UserDetailsService userDetailsService;

	private final AuthorizationCodeServices authorizationCodeServices;

	private final TokenStore redisTokenStore;

	private final TokenEnhancer tokenEnhancer;

	private final ObjectMapper objectMapper;

	@Override
	@SneakyThrows
	public void configure(ClientDetailsServiceConfigurer clients) {
		clients.withClientDetails(clientDetailsServiceImpl);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
		oauthServer.allowFormAuthenticationForClients()
				.authenticationEntryPoint(new ExtendCommenceAuthExceptionEntryPoint(objectMapper))
				.checkTokenAccess("isAuthenticated()");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints.allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST).tokenServices(tokenServices())
				.tokenStore(redisTokenStore).tokenEnhancer(tokenEnhancer).userDetailsService(userDetailsService)
				.authorizationCodeServices(authorizationCodeServices).authenticationManager(authenticationManagerBean)
				.reuseRefreshTokens(false).pathMapping("/oauth/confirm_access", "/token/confirm_access")
				.exceptionTranslator(new ExtendWebResponseExceptionTranslator());
	}

	@Bean
	public ExtendCustomTokenServices tokenServices() {
		ExtendCustomTokenServices tokenServices = new ExtendCustomTokenServices();
		tokenServices.setTokenStore(redisTokenStore);
		tokenServices.setSupportRefreshToken(true);
		tokenServices.setReuseRefreshToken(false);
		tokenServices.setClientDetailsService(clientDetailsServiceImpl);
		tokenServices.setTokenEnhancer(tokenEnhancer);
		addUserDetailsService(tokenServices, userDetailsService);
		return tokenServices;
	}

	private void addUserDetailsService(ExtendCustomTokenServices tokenServices, UserDetailsService userDetailsService) {
		if (userDetailsService != null) {
			PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
			provider.setPreAuthenticatedUserDetailsService(new UserDetailsByNameServiceWrapper<>(userDetailsService));
			tokenServices.setAuthenticationManager(new ProviderManager(Collections.singletonList(provider)));
		}
	}

}
