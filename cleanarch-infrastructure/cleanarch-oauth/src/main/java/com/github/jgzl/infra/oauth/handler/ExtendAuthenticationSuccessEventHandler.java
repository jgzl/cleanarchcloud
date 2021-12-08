package com.github.jgzl.infra.oauth.handler;

import com.github.jgzl.common.core.constant.SecurityConstants;
import com.github.jgzl.common.core.util.KeyStrResolver;
import com.github.jgzl.common.core.util.WebUtils;
import com.github.jgzl.common.log.util.SysLogUtils;
import com.github.jgzl.common.security.handler.AuthenticationSuccessHandler;
import com.github.jgzl.infra.upms.api.dto.SysLogDTO;
import com.github.jgzl.infra.upms.api.feign.RemoteLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lihaifeng
 * @date 2018/10/8
 */
@Slf4j
@Component
@AllArgsConstructor
public class ExtendAuthenticationSuccessEventHandler implements AuthenticationSuccessHandler {

	private final RemoteLogService logService;

	private final KeyStrResolver tenantKeyStrResolver;

	/**
	 * 处理登录成功方法
	 * <p>
	 * 获取到登录的authentication 对象
	 * @param authentication 登录对象
	 * @param request 请求
	 * @param response 返回
	 */
	@Async
	@Override
	public void handle(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
		String username = authentication.getName();
		SysLogDTO sysLog = SysLogUtils.getSysLog(request, username);
		sysLog.setTitle(username + "用户登录");
		sysLog.setParams(username);
		String header = request.getHeader(HttpHeaders.AUTHORIZATION);
		sysLog.setServiceId(WebUtils.extractClientId(header).orElse("N/A"));
		sysLog.setTenantId(Integer.parseInt(tenantKeyStrResolver.key()));

		logService.saveLog(sysLog, SecurityConstants.FROM_IN);
		log.info("用户：{} 登录成功", username);
	}

}
