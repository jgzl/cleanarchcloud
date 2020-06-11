package com.gitee.application.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
	@RequestMapping({"/","/index"})
	public String idx(){
		return "Welcome to index!!";
	}
}
