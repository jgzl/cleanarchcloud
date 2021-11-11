package com.github.jgzl.common.data.tenant;
import com.github.jgzl.common.core.constant.CommonConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lihaifeng
 * @date 2020/9/14
 */
@Slf4j
public class FeignTenantInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate requestTemplate) {
		if (TenantContextHolder.getTenantCode() == null) {
			log.debug("TTL 中的 租户ID为空，feign拦截器 >> 跳过");
			return;
		}
		requestTemplate.header(CommonConstants.TENANT_CODE, TenantContextHolder.getTenantCode());
	}

}
