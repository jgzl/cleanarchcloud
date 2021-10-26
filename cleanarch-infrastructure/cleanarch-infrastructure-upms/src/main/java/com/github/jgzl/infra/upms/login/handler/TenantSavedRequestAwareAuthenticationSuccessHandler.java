package com.github.jgzl.infra.upms.login.handler;
import com.github.jgzl.common.core.util.KeyStrResolver;
import com.github.jgzl.common.core.util.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户名密码登录成功返回
 * 增强成功回调增加租户上下文避免极端情况下丢失问题
 * @author lihaifeng
 * 2019/7/3 14:30
 * @see SavedRequestAwareAuthenticationSuccessHandler
 */
@Slf4j
@Component
public class TenantSavedRequestAwareAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private static final RequestCache requestCache = new HttpSessionRequestCache();

	@Override
	public void onAuthenticationSuccess(final HttpServletRequest request,
			final HttpServletResponse response,
			final Authentication authentication) throws IOException, ServletException {

		SavedRequest savedRequest = this.requestCache.getRequest(request, response);
		if (savedRequest == null) {
			super.onAuthenticationSuccess(request, response, authentication);
		}

		if (isAlwaysUseDefaultTargetUrl()) {
			this.requestCache.removeRequest(request, response);
			super.onAuthenticationSuccess(request, response, authentication);
		}
		else {
			this.clearAuthenticationAttributes(request);
			// 增加租户信息
			assert savedRequest != null;
			String redirectUrl = savedRequest.getRedirectUrl();
			log.info("登录成功,重定向url为{}",redirectUrl);
			String targetUrl;
			if (redirectUrl.endsWith("/")) {
				targetUrl = redirectUrl + "?TENANT-ID="
						+ SpringContextHolder.getBean(KeyStrResolver.class).key();
			}else {
				targetUrl = redirectUrl + "&TENANT-ID="
						+ SpringContextHolder.getBean(KeyStrResolver.class).key();
			}
			this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
		}
	}
}
