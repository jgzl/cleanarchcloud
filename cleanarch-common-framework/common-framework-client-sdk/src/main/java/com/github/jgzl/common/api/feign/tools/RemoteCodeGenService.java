package com.github.jgzl.common.api.feign.tools;

import com.github.jgzl.common.core.constant.ServiceNameConstants;
import com.github.jgzl.common.core.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author lihaifeng
 */
@FeignClient(contextId = ServiceNameConstants.TOOLS_SERVICE,value = ServiceNameConstants.TOOLS_SERVICE)
public interface RemoteCodeGenService {

	/**
	 * 心跳接口
	 * @return
	 */
	@GetMapping("/heartbeat")
	public Result<String> heartbeat();
}
