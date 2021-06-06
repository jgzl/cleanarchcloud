package com.github.jgzl.application.codegen.controller;

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
	public String index() {
		return "hello,codegen!!!";
	}
}
