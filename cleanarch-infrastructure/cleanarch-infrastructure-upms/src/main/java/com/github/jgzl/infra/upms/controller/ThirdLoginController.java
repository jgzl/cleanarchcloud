package com.github.jgzl.infra.upms.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.jgzl.common.api.dataobject.SysUser;
import com.github.jgzl.common.api.vo.SysUserVo;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.core.constant.SecurityConstants;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.security.util.SecurityUtils;
import com.github.jgzl.infra.upms.core.PathConstants;
import com.github.jgzl.infra.upms.dataobject.SysSocialUser;
import com.github.jgzl.infra.upms.service.SysSocialUserService;
import com.github.jgzl.infra.upms.service.SysUserService;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 第三方登录控制器
 */
@Slf4j
@Controller
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ThirdLoginController {

	private final AuthRequestFactory factory;

	/**
	 * 查看(所有的第三方登录平台key)
	 * @return
	 */
	@GetMapping(value = PathConstants.LOGIN_MODULE_THIRD_PATH)
	@ResponseBody
	public Result<List<String>> list() {
		return Result.ok(factory.oauthList());
	}

	/**
	 * 登录(通过第三方平台key)
	 * @param type
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = PathConstants.LOGIN_THIRD_LOGIN_URL)
	@ResponseBody
	public void login(@PathVariable String type, HttpServletRequest request, HttpServletResponse response) throws IOException {
		AuthRequest authRequest = factory.get(type);
		String redirectUrl = request.getParameter(SecurityConstants.REDIRECT_URL);
		if (StrUtil.isBlank(redirectUrl)) {
			redirectUrl = AuthStateUtils.createState();
		}
		response.sendRedirect(authRequest.authorize(redirectUrl));
	}
}