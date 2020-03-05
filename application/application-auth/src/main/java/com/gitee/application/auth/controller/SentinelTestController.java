package com.gitee.application.auth.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lihaifeng
 */
@RestController
@RequestMapping("test")
public class SentinelTestController {
	@GetMapping("ping")
	public String ping() {
		return "pong";
	}

	@GetMapping("name")
	public String query(@RequestParam(value = "name") String name) {
		return "name:[" + name + "]";
	}
}
