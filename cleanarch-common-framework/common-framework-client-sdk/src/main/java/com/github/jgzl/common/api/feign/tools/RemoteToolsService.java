package com.github.jgzl.common.api.feign.tools;

import com.github.jgzl.common.api.vo.OptLogDTO;
import com.github.jgzl.common.core.constant.SecurityConstants;
import com.github.jgzl.common.core.constant.ServiceNameConstants;
import com.github.jgzl.common.core.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author lihaifeng
 * @date 2020/6/28
 */
@FeignClient(contextId = "remoteToolsService", value = ServiceNameConstants.TOOLS_SERVICE, decode404 = true, fallback = RemoteToolsService.RemoteToolsServiceFallback.class)
public interface RemoteToolsService {

	/**
	 * 添加埋点
	 *
	 * @param dto 埋点信息
	 * @return 添加结果
	 */
	@PostMapping("/opt_logs")
	Result<String> saveLog(@RequestBody OptLogDTO dto, @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 心跳接口
	 * @return
	 */
	@GetMapping("/heartbeat")
	Result<String> heartbeat();

	@Component
	@RequiredArgsConstructor
	class RemoteToolsServiceFallback implements RemoteToolsService {

		@Override
		public Result<String> saveLog(OptLogDTO dto, String from) {
			return Result.fail("保存日志失败");
		}

		@Override
		public Result<String> heartbeat() {
			return Result.fail("心跳失败");
		}
	}
}
