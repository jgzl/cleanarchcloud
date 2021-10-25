package com.github.jgzl.infra.upms.controller;
import cn.hutool.json.JSONUtil;
import com.github.jgzl.common.core.util.Result;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
	public void login(@PathVariable String type, HttpServletResponse response) throws IOException {
		AuthRequest authRequest = factory.get(type);
		response.sendRedirect(authRequest.authorize(AuthStateUtils.createState()));
	}

	/**
	 * 第三方平台回调地址(通过第三方平台key)
	 * @param type
	 * @param callback
	 * @return
	 */
	@RequestMapping("/{type}/callback")
	public AuthResponse login(@PathVariable String type, AuthCallback callback) {
		AuthRequest authRequest = factory.get(type);
		AuthResponse response = authRequest.login(callback);
		log.info("【response】= {}", JSONUtil.toJsonStr(response));
		return response;
	}

}