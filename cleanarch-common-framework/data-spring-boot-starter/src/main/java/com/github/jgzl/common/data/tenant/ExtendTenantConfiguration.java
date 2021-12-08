package com.github.jgzl.common.data.tenant;

import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;

/**
 * @author lihaifeng
 * @date 2020/4/29
 * <p>
 * 租户信息拦截
 */
@Configuration
public class ExtendTenantConfiguration {

	@Bean
	public RequestInterceptor pigxFeignTenantInterceptor() {
		return new ExtendFeignTenantInterceptor();
	}

	@Bean
	public ClientHttpRequestInterceptor pigxTenantRequestInterceptor() {
		return new TenantRequestInterceptor();
	}

}
