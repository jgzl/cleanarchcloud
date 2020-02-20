package com.gitee.application.codegen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
	@GetMapping({"/", "/index"})
	@ResponseBody
	public String index() {
		return "hello,codegen!!!";
	}
}
