package com.github.jgzl.infra.upms.controller;

import com.github.jgzl.common.core.util.Result;
import io.swagger.annotations.Api;
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
	public Result idx(){
		return Result.ok();
	}
}
