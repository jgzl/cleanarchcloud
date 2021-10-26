package com.github.jgzl.infra.upms.controller;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.jgzl.infra.upms.core.PathConstants;
import com.github.jgzl.infra.upms.service.SysUserService;
import com.github.jgzl.infra.upms.service.SysOauthClientDetailsService;
import com.github.jgzl.common.cache.support.CustomRedisRepository;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.security.util.SecurityUtils;
import com.github.jgzl.common.api.vo.SysOauthClientDetailsVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Pattern;
import java.util.Map;
import static com.github.jgzl.common.core.constant.RegexConstants.EMAIL_REG;
import static com.github.jgzl.common.core.constant.RegexConstants.MOBILE_REG;

/**
 * 登录控制器
 * @author lihaifeng
 * @date 2018年03月10日
 */
@Slf4j
@RestController
public class LoginController {

	@Autowired
	private CustomRedisRepository redisRepository;

	@Autowired
	private SysUserService userService;

	@Autowired
	private SysOauthClientDetailsService oauthClientService;

	@Autowired
	private ConsumerTokenServices consumerTokenServices;

	/**
	 * 认证页面
	 *
	 * @param modelAndView
	 * @param error        表单登录失败处理回调的错误信息
	 * @return ModelAndView
	 */
	@GetMapping(PathConstants.LOGIN_PAGE_URL)
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
			String additionalInformation = clientDetails.getAdditionalInformation();
			if (StrUtil.isNotBlank(additionalInformation)) {
				// 从扩展信息中获取相应的应用访问地址和应用名称
				JSONObject jsonObject = JSON.parseObject(additionalInformation);
				String website = jsonObject.getString("website");
				String appName = jsonObject.getString("appName");
				modelAndView.addObject("website",website);
				modelAndView.addObject("appName",appName);
			}else {
				modelAndView.addObject("website", "https://www.baidu.com");
				modelAndView.addObject("appName", "百度");
			}
			modelAndView.addObject("user", SecurityUtils.getUser());
		}

		modelAndView.setViewName("ftl/confirm");
		return modelAndView;
	}

	/**
	 * 发送手机验证码(手机登录)
	 * @param mobile
	 * @return
	 */
	@GetMapping(PathConstants.LOGIN_MOBILE_CODE_URL)
	@ResponseBody
	public Result mobileCode(@Pattern(regexp = MOBILE_REG, message = "请输入正确的手机号") @RequestParam String mobile) {
		Object tempCode = redisRepository.get(CacheConstants.DEFAULT_CODE_KEY + mobile);
		if (tempCode != null) {
			log.error("用户:{},验证码{},未失效", mobile, tempCode);
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
	 * 发送邮箱验证码(邮箱登录)
	 * @param email
	 * @return
	 */
	@GetMapping(PathConstants.LOGIN_EMAIL_CODE_URL)
	@ResponseBody
	public Result emailCode(@Pattern(regexp = EMAIL_REG, message = "请输入正确的邮箱") @RequestParam String email) {
		Object tempCode = redisRepository.get(CacheConstants.DEFAULT_CODE_KEY + email);
		if (tempCode != null) {
			log.error("用户:{},验证码{},未失效", email, tempCode);
			return Result.failed("验证码: " + tempCode + " 未失效，请失效后再次申请");
		}
		if (userService.findUserByEmail(email) == null) {
			log.error("根据用户邮箱:{}, 查询用户为空", email);
			return Result.failed("邮箱不存在");
		}
		String code = RandomUtil.randomNumbers(6);
		log.info("邮箱发送请求消息中心 -> 邮箱:{} -> 验证码：{}", email, code);
		redisRepository
				.setExpire(CacheConstants.DEFAULT_CODE_KEY + email, code, CacheConstants.DEFAULT_EXPIRE_SECONDS);
		return Result.ok(code);
	}

}
