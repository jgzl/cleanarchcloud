package com.github.jgzl.application.auth.controller;

import cn.hutool.json.JSONArray;
import com.github.jgzl.application.auth.service.SysRouteConfService;
import com.github.jgzl.common.core.util.Result;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 路由
 *
 * @author lengleng
 * @date 2018-11-06 10:17:18
 */
@RestController
@AllArgsConstructor
@RequestMapping("/route")
@Api(value = "route", tags = "动态路由管理模块")
public class SysRouteConfController {

	private final SysRouteConfService sysRouteConfService;

	/**
	 * 获取当前定义的路由信息
	 * @return
	 */
	@GetMapping
	public Result listRoutes() {
		return Result.ok(sysRouteConfService.list());
	}

	/**
	 * 修改路由
	 * @param routes 路由定义
	 * @return
	 */
	@PutMapping
	public Result updateRoutes(@RequestBody JSONArray routes) {
		return Result.ok(sysRouteConfService.updateRoutes(routes));
	}

}
