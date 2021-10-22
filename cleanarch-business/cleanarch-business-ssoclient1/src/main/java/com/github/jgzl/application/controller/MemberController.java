package com.github.jgzl.application.controller;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 管理成员
 * @author lihaifeng
 * @date 2018/1/27
 * demo controller
 */
@Api(value = "member页面")
@Controller
@RequestMapping("/member")
public class MemberController {

	/**
	 * 跳转列表
	 * @return
	 */
	@GetMapping("/list")
	public String list() {
		return "member/list";
	}

	/**
	 * 登录用户信息
	 * @param principal
	 * @return
	 */
	@ApiOperation(value = "登录用户信息")
	@GetMapping("/info")
	@ResponseBody
	public Principal info(Principal principal) {
		return principal;
	}

	/**
	 * 登录用户信息
	 * @param authentication
	 * @return
	 */
	@ApiOperation(value = "登录用户信息")
	@GetMapping("/me")
	@ResponseBody
	public Authentication me(Authentication authentication) {
		return authentication;
	}

	/**
	 * 添加成员
	 * @return
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@ResponseBody
	@PostMapping("/add")
	public String add() {
		return "admin add";
	}

	/**
	 * 成员详情
	 * @return
	 */
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@ResponseBody
	@GetMapping("/detail")
	public String detail() {
		return "admin detail";
	}
}