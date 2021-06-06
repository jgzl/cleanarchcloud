package com.github.jgzl.application.client.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;

/**
 * @author cyxinda
 * @create 2020-05-29 14:02
 * @desc skywalking测试中央控制层
 **/
@RestController
public class TestController {
	@RequestMapping("/uid/{count}")
	public String getUidList(@PathVariable("count") String countStr, HttpServletRequest request) {
		System.out.println("counter:::::"+countStr);
		return "OK";
	}
}