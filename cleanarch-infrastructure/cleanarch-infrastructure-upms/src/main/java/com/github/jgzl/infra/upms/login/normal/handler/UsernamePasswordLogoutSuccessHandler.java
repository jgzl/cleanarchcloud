package com.github.jgzl.infra.upms.login.normal.handler;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.github.jgzl.common.core.constant.SecurityConstants;
import com.github.jgzl.common.core.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 用户名密码退出登录成功处理
 *
 * @author lihaifeng
 * 2019/7/5 17:27
 */
@Slf4j
@Component
public class UsernamePasswordLogoutSuccessHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) throws IOException {
		// 获取请求参数中是否包含 回调地址
		String redirectUrl = request.getParameter(SecurityConstants.REDIRECT_URL);
		String referer = request.getHeader(HttpHeaders.REFERER);

		if (StrUtil.isNotBlank(redirectUrl)) {
			response.sendRedirect(redirectUrl);
		}
		else if (StrUtil.isNotBlank(referer)) {
			// 默认跳转referer 地址
			response.sendRedirect(referer);
		}
	}
}
