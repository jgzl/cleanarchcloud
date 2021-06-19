package com.github.jgzl.application.auth.controller;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.github.jgzl.application.auth.core.PathConstants;
import com.github.jgzl.application.auth.service.SysUserService;
import com.github.jgzl.application.auth.service.client.SysOauthClientDetailsService;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.data.redis.CustomRedisRepository;
import com.github.jgzl.common.security.util.SecurityUtils;
import com.github.jgzl.common.security.vo.SysOauthClientDetailsVo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Pattern;
import java.io.IOException;
import java.util.Map;

/**
 * 登录控制器
 * @author lihaifeng
 * @date 2018年03月10日
 */
@Slf4j
@Api(tags = "登录控制器")
@RestController
public class LoginController {

	private static final String MOBILE_REG = "^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$";

	@Autowired
	private CustomRedisRepository redisRepository;

	@Autowired
	private SysUserService userService;

	@Autowired
	private SysOauthClientDetailsService oauthClientService;

	/**
	 * 认证页面
	 *
	 * @param modelAndView
	 * @param error        表单登录失败处理回调的错误信息
	 * @return ModelAndView
	 */
	@GetMapping(PathConstants.LOGIN_URL)
	public ModelAndView require(ModelAndView modelAndView, @RequestParam(required = false) String error) {
		modelAndView.setViewName("ftl/login");
		modelAndView.addObject("error", error);
		return modelAndView;
	}

	/**
	 * 确认授权页面
	 *
	 * @param request
	 * @param session
	 * @param modelAndView
	 * @return
	 */
	@GetMapping(PathConstants.LOGIN_CONFIRM_URL)
	public ModelAndView confirm(HttpServletRequest request, HttpSession session, ModelAndView modelAndView) {
		Map<String, Object> scopeList = (Map<String, Object>) request.getAttribute("scopes");
		modelAndView.addObject("scopeList", scopeList.keySet());

		Object auth = session.getAttribute("authorizationRequest");
		if (auth != null) {
			AuthorizationRequest authorizationRequest = (AuthorizationRequest) auth;
			SysOauthClientDetailsVo clientDetails = oauthClientService.getVo(authorizationRequest.getClientId());
			if (StrUtil.isNotBlank(clientDetails.getAdditionalInformation())) {
				// 从扩展信息中获取相应的应用访问地址和应用名称
			}
			modelAndView.addObject("website", "https://www.baidu.com");
			modelAndView.addObject("appName", "DemoApp");
			modelAndView.addObject("user", SecurityUtils.getUser());
		}

		modelAndView.setViewName("ftl/confirm");
		return modelAndView;
	}

    /**
     * 需要重定向到请求之前的地址，则使用此登出方法
     */
    @GetMapping(PathConstants.LOGIN_LOGOUT_URL)
    public void removeToken(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, null, null);
        try {
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	/**
	 * 发送手机验证码(手机普通登录，oauth2登录)
	 * @param mobile
	 * @return
	 */
	@GetMapping(PathConstants.LOGIN_MOBILE_CODE_URL)
	@ResponseBody
	public Result smsCode(@Pattern(regexp = MOBILE_REG, message = "请输入正确的手机号") @RequestParam String mobile) {
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
}
