package com.github.jgzl.infra.upms.login.config;

import com.github.jgzl.common.api.vo.UserVo;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.core.constant.SecurityConstants;
import com.github.jgzl.common.core.properties.SecurityConfigProperties;
import com.github.jgzl.infra.upms.login.service.RedisAuthenticationCodeServices;
import com.github.jgzl.infra.upms.service.impl.UserNameUserDetailsServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.security.KeyPair;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@AllArgsConstructor
@Configuration
public class CustomOauth2TokenConfiguration {

	private final SecurityConfigProperties securityProperties;

	private final UserNameUserDetailsServiceImpl userNameUserDetailsService;

	private final RedisConnectionFactory redisConnectionFactory;

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
			if (SecurityConstants.CLIENT_CREDENTIALS.equals(authentication.getOAuth2Request().getGrantType())) {
				return accessToken;
			}
			Map<String, Object> additionalInfo = new LinkedHashMap<>(accessToken.getAdditionalInformation());
			final Object principal = authentication.getPrincipal();
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
			additionalInfo.put(SecurityConstants.ACTIVE,Boolean.TRUE);
			((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
			return accessToken;
		};
	}

	@Bean
	@Primary
	public AuthorizationServerTokenServices tokenServices() {

		DefaultTokenServices tokenServices = new DefaultTokenServices();
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();

		tokenServices.setSupportRefreshToken(true);
		tokenServices.setReuseRefreshToken(false);
		tokenServices.setTokenStore(tokenStore());
		tokenServices.setAccessTokenValiditySeconds(securityProperties.getAccessTokenValiditySeconds());
		tokenServices.setRefreshTokenValiditySeconds(securityProperties.getRefreshTokenValiditySeconds());

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
				(securityProperties.getKeyStore().getLocation(),
						securityProperties.getKeyStore().getSecret().toCharArray())
				.getKeyPair(securityProperties.getKeyStore().getAlias());
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

	/**
	 * 自定义AuthorizationCodeServices实现类来将auth_code 存放在redis中
	 */
	@Bean
	public RedisAuthenticationCodeServices redisAuthenticationCodeServices() {
		return new RedisAuthenticationCodeServices(redisConnectionFactory);
	}
}
