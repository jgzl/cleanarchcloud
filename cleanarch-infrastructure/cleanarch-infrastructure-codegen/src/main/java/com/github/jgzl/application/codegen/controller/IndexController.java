package com.github.jgzl.application.codegen.controller;

import com.github.jgzl.common.core.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 首页
 * @author Administrator
 */
@Controller
public class IndexController {

	/**
	 * 默认返回
	 * @return
	 */
	@GetMapping("/")
	@ResponseBody
	public Result<String> index() {
		return Result.ok("hello,codegen!!!");
	}
}
