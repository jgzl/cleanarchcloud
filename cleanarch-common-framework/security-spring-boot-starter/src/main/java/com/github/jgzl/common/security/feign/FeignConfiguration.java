package com.github.jgzl.common.security.feign;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.commons.security.AccessTokenContextRelay;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import feign.Feign;
import feign.RequestInterceptor;
/**
 * fegin 配置增强
 *
 * @author lihaifeng
 */
@Configuration
@ConditionalOnClass(Feign.class)
public class FeignConfiguration {
	@Bean
	@ConditionalOnProperty("security.oauth2.client.client-id")
	public RequestInterceptor oauth2FeignRequestInterceptor(
			@Qualifier("oauth2ClientContext") OAuth2ClientContext oAuth2ClientContext,
			OAuth2ProtectedResourceDetails resource,
			AccessTokenContextRelay accessTokenContextRelay) {
		return new Oauth2FeignClientInterceptor(oAuth2ClientContext, resource, accessTokenContextRelay);
	}
}
