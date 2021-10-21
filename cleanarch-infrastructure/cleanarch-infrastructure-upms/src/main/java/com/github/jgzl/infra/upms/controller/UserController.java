package com.github.jgzl.infra.upms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.infra.upms.service.SysUserService;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.security.validate.Add;
import com.github.jgzl.common.security.vo.SysUserVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * 用户Controller
 *
 * @author lihaifeng
 * 2019/7/4 15:44
 */
@Api(tags = "用户控制器")
@Slf4j
@Controller
@Validated
@RequestMapping("/user")
public class UserController {

	@Autowired
	private SysUserService userService;

	/**
	 * 查询登录用户
	 *
	 * @param authentication 信息
	 * @return 用户信息
	 */
	@GetMapping("/info")
	@ResponseBody
	public Result userInfo(Authentication authentication) {
		if (authentication == null) {
			return null;
		}
		return Result.ok(authentication.getPrincipal());
	}

	/**
	 * 查询单个用户
	 *
	 * @param id 用户ID
	 * @return
	 */
	@GetMapping("/{id}")
	public Result get(@PathVariable("id") String id) {

		return Result.ok(userService.getVo(id));
	}

	/**
	 * 修改单个用户
	 *
	 * @param vo 用户
	 * @return
	 */
	@PutMapping
	public Result update(@Valid @RequestBody SysUserVo vo) {

		return Result.ok(userService.update(vo));
	}

	/**
	 * 新增单个用户
	 *
	 * @param vo 用户
	 * @return
	 */
	@PostMapping
	public Result add(@Validated(Add.class) @RequestBody SysUserVo vo) {

		return Result.ok(userService.add(vo));
	}

	/**
	 * 删除单个用户
	 *
	 * @param id 用户ID
	 * @return
	 */
	@DeleteMapping("/{id}")
	public Result<Boolean> delete(@PathVariable("id") String id) {
		return Result.ok(userService.delete(id));
	}

	/**
	 * 分页查询用户
	 *
	 * @param request
	 * @return
	 */
	@GetMapping
	public Result<Page<SysUserVo>> page(HttpServletRequest request, Page page) {
		userService.selectPageVo(page);
		return Result.ok(page);
	}
}
