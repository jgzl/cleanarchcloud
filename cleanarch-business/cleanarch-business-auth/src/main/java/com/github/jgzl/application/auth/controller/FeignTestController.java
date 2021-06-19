package com.github.jgzl.application.auth.controller;

import com.github.jgzl.application.auth.feign.CodeGenFeignClient;
import com.github.jgzl.common.core.util.Result;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * feign测试Controller
 */
@AllArgsConstructor
@RestController
@RequestMapping(value = "feign")
public class FeignTestController {

	private CodeGenFeignClient codeGenFeignClient;

	@GetMapping("/codegen/heartbeat")
	public Result heartbeat(){
		return codeGenFeignClient.heartbeat();
	}
}
