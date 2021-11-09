package com.github.jgzl.common.data.tenant;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;

/**
 * @author lihaifeng
 * @date 2020/4/29
 * 租户信息拦截
 */
@Configuration
public class TenantConfiguration {

	@Bean
	public RequestInterceptor feignTenantInterceptor() {
		return new FeignTenantInterceptor();
	}

	@Bean
	public ClientHttpRequestInterceptor tenantRequestInterceptor() {
		return new TenantRequestInterceptor();
	}

}
