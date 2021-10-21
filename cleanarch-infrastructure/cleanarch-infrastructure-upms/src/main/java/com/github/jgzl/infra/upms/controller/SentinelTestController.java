package com.github.jgzl.infra.upms.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试Sentinel
 * @author lihaifeng
 */
@Api(tags = "Sentinel测试控制器")
@RestController
@RequestMapping("test")
public class SentinelTestController {
	/**
	 * ping
	 * @return
	 */
	@GetMapping("ping")
	public String ping() {
		return "pong";
	}

	/**
	 * 查找数据
	 * @param name
	 * @return
	 */
	@GetMapping("name")
	public String query(@RequestParam(value = "name") String name) {
		return "name:[" + name + "]";
	}
}
