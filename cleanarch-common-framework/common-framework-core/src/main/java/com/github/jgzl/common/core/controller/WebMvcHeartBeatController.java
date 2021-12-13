package com.github.jgzl.common.core.controller;

import com.github.jgzl.common.core.util.R;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication.Type;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/heartbeat")
@ConditionalOnWebApplication(type = Type.SERVLET)
public class WebMvcHeartBeatController {
	@GetMapping
	public R<String> heartbeat(){
		return R.ok("服务端成功接收心跳请求");
	}
}