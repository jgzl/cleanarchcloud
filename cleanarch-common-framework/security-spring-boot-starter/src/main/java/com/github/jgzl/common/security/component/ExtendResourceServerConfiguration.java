package com.github.jgzl.common.security.component;
import com.github.jgzl.common.security.properties.FrameworkSecurityProperties;
import com.github.jgzl.common.core.constant.CacheConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import java.util.List;

/**
 * @author lihaifeng
 * @date 2020/6/22
 * 1. 支持remoteTokenServices 负载均衡
 * 2. 支持 获取用户全部信息
 */
@Slf4j
public class ExtendResourceServerConfiguration extends ResourceServerConfigurerAdapter {

	@Autowired
	private RedisConnectionFactory redisConnectionFactory;

	@Autowired
	private FrameworkSecurityProperties securityProperties;

	@Autowired
	private PermitAllUrlResolver permitAllUrlResolver;

	@Autowired
	private ExtendResourceAuthExceptionEntryPoint authExceptionEntryPoint;

	@Override
	public void configure(HttpSecurity http) throws Exception {
		ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http
				.authorizeRequests();
		final List<String> urls = securityProperties.getUrlPermitAll();
		urls.forEach(url -> registry.antMatchers(url).permitAll());
		permitAllUrlResolver.registry(registry);
		registry.anyRequest().authenticated().and().cors().and().csrf().disable();
	}

	@Override
	public void configure(ResourceServerSecurityConfigurer resources) {
		DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
		UserAuthenticationConverter userTokenConverter = new ExtendUserAuthenticationConverter();
		accessTokenConverter.setUserTokenConverter(userTokenConverter);
		resources.authenticationEntryPoint(authExceptionEntryPoint);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public TokenStore tokenStore() {
		final RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
		tokenStore.setSerializationStrategy(new ExtendStringSerializationStrategy());
		tokenStore.setPrefix(CacheConstants.REDIS_TOKEN_PREFIX);
		return tokenStore;
	}
}
