package com.github.jgzl.common.core.controller;
import com.github.jgzl.common.core.util.Result;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heartbeat")
@ConditionalOnWebApplication(type = Type.SERVLET)
public class HeartBeatController {
	@GetMapping
	public Result<String> heartbeat(){
		return Result.ok(null,"成功接收心跳请求");
	}
}