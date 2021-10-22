package com.github.jgzl.infra.codegen.controller;

import com.github.jgzl.common.core.util.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 首页
 * @author lihaifeng
 */
@Controller
public class IndexController {

	/**
	 * 返回服务名
	 * @return
	 */
	@GetMapping("/service")
	@ResponseBody
	public Result<String> index() {
		return Result.ok("codegen");
	}

}
