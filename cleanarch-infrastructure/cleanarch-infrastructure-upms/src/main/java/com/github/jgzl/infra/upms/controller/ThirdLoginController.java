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
import com.github.jgzl.infra.upms.dataobject.SysSocialUser;
import com.github.jgzl.infra.upms.login.token.third.ThirdLoginAuthenticationToken;
import com.github.jgzl.infra.upms.service.SysSocialUserAuthService;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 第三方登录控制器
 */
@Slf4j
@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ThirdLoginController {

	private final AuthRequestFactory factory;
	private final SysUserService sysUserService;
	private final SysSocialUserService sysSocialUserService;
	private final SysSocialUserAuthService sysSocialUserAuthService;
	private final RedisTemplate<String,String> redisTemplate;
	private final AuthenticationManager authenticationManager;

	/**
	 * 查看(所有的第三方登录平台key)
	 * @return
	 */
	@GetMapping
	public Result<List<String>> list() {
		return Result.ok(factory.oauthList());
	}

	/**
	 * 登录(通过第三方平台key)
	 * @param type
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/login/{type}")
	public void login(@PathVariable String type, HttpServletRequest request, HttpServletResponse response) throws IOException {
		AuthRequest authRequest = factory.get(type);
		String redirectUrl = request.getParameter(SecurityConstants.REDIRECT_URL);
		if (StrUtil.isBlank(redirectUrl)) {
			redirectUrl = AuthStateUtils.createState();
		}
		response.sendRedirect(authRequest.authorize(redirectUrl));
	}

	/**
	 * 第三方平台回调地址(通过第三方平台key)
	 * <img src="https://justauth.wiki/_media/extended/justauth_integrated_with_the_existing_account_system.png"/>
	 * @param type
	 * @param callback
	 * @return
	 */
	@RequestMapping("/{type}/callback")
	public void login(@PathVariable String type, AuthCallback callback, HttpServletRequest request, HttpServletResponse response) throws IOException {
		AuthRequest authRequest = factory.get(type);
		AuthResponse authResponse = authRequest.login(callback);
		String redirectUrl = callback.getState();
		Object data = authResponse.getData();
		log.info("【response】= {}", JSONUtil.toJsonStr(data));
		if (data instanceof AuthUser) {
			AuthUser authUser = (AuthUser) data;
			String uuid = authUser.getUuid();
			SysSocialUser socialUser = sysSocialUserService.getOne(
					Wrappers.query(
							SysSocialUser.builder()
									.uuid(uuid)
									.source(authUser.getSource())
									.build()
					)
			);
			SysUserVo userVo = SecurityUtils.getUser();
			if (userVo!=null) {
				if (socialUser==null) {
					// 当前用户已经登录,第三方用户数据UUID+SOURCE不匹配,1.当前用户未绑定过第三方账号,2.当前用户绑定过第三方账号，弹出是否取消原有绑定第三方用户弹框(新增取消绑定,并绑定新第三方用户接口,第三方数据进入redis,用于绑定账号数据)
					List<SysSocialUser> socialUsers = sysSocialUserService.findSocialUserByUserIdAndSource(userVo.getUserId(), authUser.getSource());
					// 当前用户未绑定过第三方账号
					if (CollUtil.isEmpty(socialUsers)) {
						// 第三方账户绑定当前用户
						sysUserService.bindSocialUser(authUser,userVo.getUserId());
						response.sendRedirect(redirectUrl);
					}else if (socialUsers.size()==1) {
						socialUser = socialUsers.get(0);
						HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
						hashOperations.put(CacheConstants.PREFIX+"thirdLogin"+CacheConstants.SPLIT+authUser.getSource(),uuid,JSONUtil.toJsonStr(socialUser));
						response.sendRedirect(String.format("/oauth/rebind?user_id=%s&third_login_id=%s&redirect_url=%s",userVo.getUserId(),uuid,redirectUrl));
					}else {
						//TODO 1.业务数据报警,理论上应当不存在这种情形
						log.error("当前主账号绑定了多个同一第三方平台的不同账号");
						response.sendRedirect(redirectUrl);
					}
				}else{
					// 当前用户已经登录,第三方用户数据UUID+SOURCE匹配,则直接返回redirect_url
					response.sendRedirect(redirectUrl);
				}
			}else {
				// 当前用户未登录,根据第三方用户数据,查找到关联的用户信息,则自动进行登录;查找不到关联的用户信息,第三方数据进入redis,用于绑定已有账号或者新账号数据
				List<SysUser> sysUsers = sysUserService.findUserBySocialUserUuidAndSource(uuid, authUser.getSource());
				if (CollUtil.isEmpty(sysUsers)) {
					HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
					hashOperations.put(CacheConstants.PREFIX+"thirdLogin"+CacheConstants.SPLIT+authUser.getSource(),uuid,JSONUtil.toJsonStr(socialUser));
					response.sendRedirect(String.format("/oauth/bind?third_login_id=%s&redirect_url=%s",uuid,redirectUrl));
				}else {
					SysUser user = sysUsers.get(0);
					//进行授权登录
					ThirdLoginAuthenticationToken token = new ThirdLoginAuthenticationToken(user.getUsername(),null);
					try{
						token.setDetails(new WebAuthenticationDetails(request));
						Authentication authenticatedUser = authenticationManager.authenticate(token);
						SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
						request.getSession().setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
					} catch( AuthenticationException e ){
						log.error("Authentication failed: " ,e);
						response.sendRedirect("/login/register");
					}
					//跳到首页
					response.sendRedirect(redirectUrl);
				}
			}
		}
	}

}