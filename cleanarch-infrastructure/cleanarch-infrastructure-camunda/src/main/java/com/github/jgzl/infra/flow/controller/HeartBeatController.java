package com.github.jgzl.infra.flow.controller;

import com.github.jgzl.common.core.util.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heartbeat")
public class HeartBeatController {
	@GetMapping
	public Result<String> heartbeat(){
		return Result.ok(null,"成功接收心跳请求");
	}
}
