package com.github.jgzl.infra.upms.controller;

import com.github.jgzl.common.api.feign.tools.RemoteToolsService;
import com.github.jgzl.common.core.util.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Feign测试控制器
 */
@AllArgsConstructor
@RestController
@RequestMapping(value = "feign")
public class FeignTestController {

	private RemoteToolsService remoteToolsService;

	@GetMapping("/heartbeat")
	public Result tools_heartbeat(){
		return remoteToolsService.heartbeat();
	}
}
