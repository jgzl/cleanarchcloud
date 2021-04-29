package com.gitee.application.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gitee.common.core.util.Result;

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
