package com.github.jgzl.application.auth.feign;

import com.github.jgzl.common.core.constant.ServiceNameConstants;
import com.github.jgzl.common.core.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * camunda微服务模块远程调用
 */
@FeignClient(contextId = ServiceNameConstants.CAMUNDA_SERVICE,value = ServiceNameConstants.CAMUNDA_SERVICE)
public interface CamundaFeignClient {

	/**
	 * 心跳接口
	 * @return
	 */
	@GetMapping("/heartbeat")
	public Result<String> heartbeat();
}
