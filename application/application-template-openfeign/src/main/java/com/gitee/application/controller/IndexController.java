package com.gitee.application.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lihaifeng
 */
@Api(value = "openfeign主页", tags = {"openfeign主页"})
@RestController
@RequestMapping
public class IndexController {

	@ApiOperation("回音接口")
	@GetMapping("/echo")
	public String echo() {
		return "echo:[message]";
	}
}
