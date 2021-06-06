package com.gitee.application.client.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页
 * @author lihaifeng
 */
@Controller
public class IndexController {
	/**
	 * 默认返回
	 * @return
	 */
	@GetMapping(value = "/")
	public String redirectToMember() {
		return "member/list";
	}
}
