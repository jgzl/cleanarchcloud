package com.github.jgzl.application.auth.custom.login.config;

import com.github.jgzl.application.auth.core.PathConstants;
import com.github.jgzl.application.auth.custom.login.exception.CustomWebResponseExceptionTranslator;
import com.github.jgzl.application.auth.custom.login.handler.UsernamePasswordAccessDeniedHandler;
import com.github.jgzl.application.auth.custom.login.handler.UsernamePasswordExceptionEntryPoint;
import com.github.jgzl.application.auth.custom.login.service.CustomClientDetailsService;
import com.github.jgzl.application.auth.custom.login.service.RedisAuthenticationCodeServices;
import com.github.jgzl.application.auth.service.user.UserNameUserDetailsServiceImpl;
import com.github.jgzl.common.core.config.SysProperties;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.core.constant.SecurityConstants;
import com.github.jgzl.common.data.redis.CustomRedisRepository;
import com.github.jgzl.common.security.vo.UserVo;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 认证服务器配置抽象
 *
 * @author lihaifeng
 * @date 2018/7/24 16:10
 */
@AllArgsConstructor
@Configuration
@EnableAuthorizationServer
public class CustomAuthorizationServerConfigration extends AuthorizationServerConfigurerAdapter {

    private final AuthenticationManager authenticationManager;

	private final SysProperties ssoProperties;

    private final CustomRedisRepository redisRepository;

    private final RedisConnectionFactory redisConnectionFactory;

    private final UserNameUserDetailsServiceImpl userNameUserDetailsService;

    private final CustomWebResponseExceptionTranslator exceptionTranslator;

    private final UsernamePasswordExceptionEntryPoint exceptionEntryPoint;

    private final UsernamePasswordAccessDeniedHandler accessDeniedHandler;

	private final DataSource dataSource;

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
                .tokenServices(tokenServices())
                .exceptionTranslator(exceptionTranslator)
				.pathMapping("/oauth/confirm_access", PathConstants.LOGIN_CONFIRM_URL)
                .authorizationCodeServices(redisAuthenticationCodeServices());
    }

    /**
     * 授权服务器端点的安全配置
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security
				//isAuthenticated() permitAll()
				.tokenKeyAccess("permitAll()")
				.checkTokenAccess("permitAll()")
                //allowFormAuthenticationForClients是为了注册clientCredentialsTokenEndpointFilter
                //clientCredentialsTokenEndpointFilter,解析request中的client_id和client_secret
                //构造成UsernamePasswordAuthenticationToken,然后通过UserDetailsService查询作简单的认证而已
                //一般是针对password模式和client_credentials 主要是让/oauth/token支持client_id以及client_secret作登录认证
                .allowFormAuthenticationForClients()
                .authenticationEntryPoint(exceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }

    /**
     * 自定义AuthorizationCodeServices实现类来将auth_code 存放在redis中
     */
    @Bean
    public RedisAuthenticationCodeServices redisAuthenticationCodeServices() {
        return new RedisAuthenticationCodeServices(redisConnectionFactory);
    }

    /**
     * jwt 生成token 定制化处理
     * <p>
     * 额外信息（这部分信息不关乎加密方式）, 添加到随token一起的additionalInformation当中
     *
     * @return TokenEnhancer
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
			final Authentication userAuthentication = authentication.getUserAuthentication();
			if (userAuthentication == null) {
				return accessToken;
			}
			Map<String, Object> additionalInfo = new LinkedHashMap<>(accessToken.getAdditionalInformation());
			final Object principal = userAuthentication.getPrincipal();
			UserVo user;
			if (principal instanceof UserVo) {
				user = (UserVo) principal;
			} else {
				final String username = (String) principal;
				user = (UserVo) userNameUserDetailsService.loadUserByUsername(username);
			}
			additionalInfo.put(SecurityConstants.LICENSE_KEY, SecurityConstants.LICENSE);
			additionalInfo.put(SecurityConstants.USER_NAME_HEADER, user.getUsername());
			additionalInfo.put(SecurityConstants.USER_ID_HEADER, user.getUserId());
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
			return accessToken;
		};
    }

    @Bean
    public DefaultTokenServices tokenServices() {

		DefaultTokenServices tokenServices = new DefaultTokenServices();
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();

		tokenServices.setSupportRefreshToken(true);
		tokenServices.setReuseRefreshToken(false);
		tokenServices.setTokenStore(tokenStore());
		tokenServices.setAccessTokenValiditySeconds(ssoProperties.getOauth2().getAccessTokenValiditySeconds());
		tokenServices.setRefreshTokenValiditySeconds(ssoProperties.getOauth2().getRefreshTokenValiditySeconds());

		tokenServices.setTokenEnhancer(tokenEnhancerChain);
		tokenEnhancerChain.setTokenEnhancers(Arrays.asList(jwtAccessTokenConverter(), tokenEnhancer()));

		return tokenServices;
	}
	/**
	 * JWT Token 生成转换器（加密方式以及加密的Token中存放哪些信息）
	 *
	 * @return
	 */
	@Bean
	public JwtAccessTokenConverter jwtAccessTokenConverter() {
		final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		KeyPair keyPair = new KeyStoreKeyFactory
				(ssoProperties.getOauth2().getKeyStore().getLocation(),
						ssoProperties.getOauth2().getKeyStore().getSecret().toCharArray())
				.getKeyPair(ssoProperties.getOauth2().getKeyStore().getAlias());
		converter.setKeyPair(keyPair);
		converter.setAccessTokenConverter(new CustomAccessTokenConverter());
		return converter;
	}

	@Bean
	public TokenStore tokenStore() {
		final RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
		tokenStore.setPrefix(CacheConstants.REDIS_TOKEN_PREFIX);
		return tokenStore;
	}
}
