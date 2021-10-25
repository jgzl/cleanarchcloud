package com.github.jgzl.common.api.feign;
import com.github.jgzl.common.core.constant.ServiceNameConstants;
import com.github.jgzl.common.core.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * codegen微服务模块远程调用
 */
@FeignClient(contextId = ServiceNameConstants.CODE_GEN_SERVICE,value = ServiceNameConstants.CODE_GEN_SERVICE)
public interface CodeGenFeignClient {

	/**
	 * 心跳接口
	 * @return
	 */
	@GetMapping("/heartbeat")
	public Result<String> heartbeat();
}
