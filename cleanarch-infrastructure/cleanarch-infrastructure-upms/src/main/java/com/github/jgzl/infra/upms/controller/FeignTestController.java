package com.github.jgzl.infra.upms.controller;

import com.github.jgzl.infra.upms.feign.CamundaFeignClient;
import com.github.jgzl.infra.upms.feign.CodeGenFeignClient;
import com.github.jgzl.common.core.util.Result;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Feign测试控制器
 */
@Api(tags = "Feign测试控制器")
@AllArgsConstructor
@RestController
@RequestMapping(value = "feign")
public class FeignTestController {

	private CodeGenFeignClient codeGenFeignClient;
	private CamundaFeignClient camundaFeignClient;

	@GetMapping("/codegen/heartbeat")
	public Result codegen_heartbeat(){
		return codeGenFeignClient.heartbeat();
	}

	@GetMapping("/camunda/heartbeat")
	public Result camunda_heartbeat(){
		return camundaFeignClient.heartbeat();
	}
}
