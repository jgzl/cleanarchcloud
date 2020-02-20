package com.gitee.application.controller;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gitee.application.template.service.EchoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author lihaifeng
 */
@Api(value = "dubbo主页", tags = {"dubbo主页"})
@RestController
public class IndexController {

	@Reference
	private EchoService echoService;

	@ApiOperation("回音接口")
	@GetMapping("/echo")
	public String echo() {
		String message = echoService.echo("李海峰");
		return message;
	}
}
