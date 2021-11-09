package com.github.jgzl.common.data.tenant;
import com.github.jgzl.common.core.constant.CommonConstants;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import java.io.IOException;

/**
 * @author lihaifeng
 * @date 2020/4/29
 * 传递 RestTemplate 请求的租户ID
 */
public class TenantRequestInterceptor implements ClientHttpRequestInterceptor {

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {

		if (TenantContextHolder.getTenantId() != null) {
			request.getHeaders().set(CommonConstants.TENANT_ID, String.valueOf(TenantContextHolder.getTenantId()));
		}

		return execution.execute(request, body);
	}

}
