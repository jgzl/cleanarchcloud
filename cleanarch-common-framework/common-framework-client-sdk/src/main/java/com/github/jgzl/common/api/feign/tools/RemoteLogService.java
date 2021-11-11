package com.github.jgzl.common.api.feign.tools;

import com.github.jgzl.common.api.vo.OptLogDTO;
import com.github.jgzl.common.core.constant.SecurityConstants;
import com.github.jgzl.common.core.constant.ServiceNameConstants;
import com.github.jgzl.common.core.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author lihaifeng
 * @date 2020/6/28
 */
@FeignClient(contextId = ServiceNameConstants.TOOLS_SERVICE, value = ServiceNameConstants.TOOLS_SERVICE, decode404 = true, fallback = RemoteLogService.RemoteLogServiceFallback.class)
public interface RemoteLogService {

	/**
	 * 添加埋点
	 *
	 * @param dto 埋点信息
	 * @return 添加结果
	 */
	@PostMapping("/opt_logs")
	Result<String> saveLog(@RequestBody OptLogDTO dto, @RequestHeader(SecurityConstants.FROM) String from);

	@Component
	@RequiredArgsConstructor
	class RemoteLogServiceFallback implements RemoteLogService {

		@Override
		public Result<String> saveLog(OptLogDTO dto, String from) {
			return Result.fail("保存日志失败");
		}
	}
}
