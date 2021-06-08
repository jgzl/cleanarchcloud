package com.github.jgzl.application.auth.controller;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.application.auth.service.SysUserService;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.data.redis.CustomRedisRepository;
import com.github.jgzl.common.security.validate.Add;
import com.github.jgzl.common.security.vo.SysUserVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;

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

    private static final String MOBILE_REG = "^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$";

    @Autowired
    private CustomRedisRepository redisRepository;

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
	 * 发送手机验证码
	 *
	 * @param mobile
	 * @return
	 */
	@GetMapping("/smsCode/{mobile}")
	@ResponseBody
	public Result smsCode(@Pattern(regexp = MOBILE_REG, message = "请输入正确的手机号") @PathVariable String mobile) {
		Object tempCode = redisRepository.get(CacheConstants.DEFAULT_CODE_KEY + mobile);
		if (tempCode != null) {
			log.error("用户:{}验证码未失效{}", mobile, tempCode);
			return Result.failed("验证码: " + tempCode + " 未失效，请失效后再次申请");
		}
		if (userService.findUserByMobile(mobile) == null) {
			log.error("根据用户手机号:{}, 查询用户为空", mobile);
			return Result.failed("手机号不存在");
		}
		String code = RandomUtil.randomNumbers(6);
		log.info("短信发送请求消息中心 -> 手机号:{} -> 验证码：{}", mobile, code);
		redisRepository
				.setExpire(CacheConstants.DEFAULT_CODE_KEY + mobile, code, CacheConstants.DEFAULT_EXPIRE_SECONDS);
		return Result.ok(code);
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
	public Result update(@Valid @RequestBody SysUserVO vo) {

		return Result.ok(userService.update(vo));
	}

	/**
	 * 新增单个用户
	 *
	 * @param vo 用户
	 * @return
	 */
	@PostMapping
	public Result add(@Validated(Add.class) @RequestBody SysUserVO vo) {

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
	public Result<Page<SysUserVO>> page(HttpServletRequest request, Page page) {
		userService.selectPageVo(page);
		return Result.ok(page);
	}
}
