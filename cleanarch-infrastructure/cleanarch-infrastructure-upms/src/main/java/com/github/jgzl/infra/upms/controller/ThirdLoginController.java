package com.github.jgzl.infra.upms.controller;
import cn.hutool.core.util.StrUtil;
import com.github.jgzl.common.core.constant.SecurityConstants;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.infra.upms.core.PathConstants;
import com.xkcoding.justauth.AuthRequestFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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