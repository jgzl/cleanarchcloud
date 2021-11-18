package com.github.jgzl.infra.upms.controller.remote;

import com.github.jgzl.common.api.feign.tools.RemoteToolsService;
import com.github.jgzl.common.core.util.Result;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Feign测试控制器
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "remote")
public class FeignTestController {

	private final RemoteToolsService remoteToolsService;

	@GetMapping("/tools/heartbeat")
	public Result tools_heartbeat(){
		return remoteToolsService.heartbeat();
	}
}
