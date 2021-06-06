package com.github.jgzl.application.auth.controller;

import com.github.jgzl.common.core.util.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主页
 */
@RestController
public class IndexController {
	/**
	 * 默认返回
	 * @return
	 */
	@RequestMapping("/")
	public ResponseEntity<Result> idx(){
		return ResponseEntity.ok(Result.ok());
	}
}
