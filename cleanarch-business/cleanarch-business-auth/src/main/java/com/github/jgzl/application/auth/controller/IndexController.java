package com.github.jgzl.application.auth.controller;

import com.github.jgzl.common.core.util.Result;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主页
 */
@Api(tags = "主页控制器")
@RestController
public class IndexController {
	/**
	 * 默认返回
	 * @return
	 */
	@GetMapping("/")
	public ResponseEntity<Result> idx(){
		return ResponseEntity.ok(Result.ok());
	}
}
