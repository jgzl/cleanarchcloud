package com.github.jgzl.infra.upms.controller;

import com.github.jgzl.common.core.util.R;
import com.github.jgzl.common.security.annotation.Inner;
import com.github.jgzl.infra.upms.service.MobileService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lihaifeng
 * @date 2018/11/14
 * <p>
 * 手机验证码
 */
@RestController
@AllArgsConstructor
@RequestMapping("/mobile")
@Api(value = "mobile", tags = "手机管理模块")
public class MobileController {

	private final MobileService mobileService;

	@Inner(value = false)
	@GetMapping("/{mobile}")
	public R sendSmsCode(@PathVariable String mobile) {
		return mobileService.sendSmsCode(mobile);
	}

}
