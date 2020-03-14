package com.gitee.application.auth.controller;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint;
import org.springframework.security.oauth2.provider.endpoint.WhitelabelApprovalEndpoint;
import org.springframework.security.oauth2.provider.endpoint.WhitelabelErrorEndpoint;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.view.RedirectView;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.gitee.application.auth.service.client.SsoOauthClientDetailsService;
import com.gitee.common.core.config.SsoProperties;
import com.gitee.common.core.util.Response;
import com.gitee.common.security.dao.SsoOauthClientDetailsDAO;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 自定义授权端点
 *
 * @author lihaifeng
 * 2019/5/17 9:56
 * @see WhitelabelApprovalEndpoint
 * @see WhitelabelErrorEndpoint
 * @see AuthorizationEndpoint
 */
@Slf4j
@Controller
@SessionAttributes({AuthorizationController.AUTHORIZATION_REQUEST_ATTR_NAME,
		AuthorizationController.ORIGINAL_AUTHORIZATION_REQUEST_ATTR_NAME})
public class AuthorizationController {

	static final String AUTHORIZATION_REQUEST_ATTR_NAME = "authorizationRequest";

	static final String ORIGINAL_AUTHORIZATION_REQUEST_ATTR_NAME = "org.springframework.security.oauth2.provider.endpoint.AuthorizationEndpoint.ORIGINAL_AUTHORIZATION_REQUEST";

	@Autowired
	private SsoOauthClientDetailsService oauthClientService;

	@Autowired
	private AuthorizationEndpoint authorizationEndpoint;

	@Autowired
	private SsoProperties ssoProperties;

	/**
	 * 自定义 确认/拒绝授权
	 *
	 * @param approvalParameters
	 * @param model
	 * @param sessionStatus
	 * @param principal
	 * @return
	 */
    @RequestMapping(value = "/oauth/custom_authorize", method = RequestMethod.POST, params = OAuth2Utils.USER_OAUTH_APPROVAL)
    public ResponseEntity<Response> approveOrDeny(@RequestParam Map<String, String> approvalParameters,
                                                  Map<String, ?> model, SessionStatus sessionStatus, Principal principal) {
        try{
            final RedirectView redirectView = (RedirectView) authorizationEndpoint.approveOrDeny(
                    approvalParameters, model, sessionStatus, principal);
            return ResponseEntity.ok(Response.success(redirectView.getUrl()));
        } catch (OAuth2Exception e) {
            log.error("确认/拒绝授权失败", e);
            return ResponseEntity.status(e.getHttpErrorCode()).body(Response.failure(e.getOAuth2ErrorCode(), e.getMessage()));
        }
    }

    /**
     * 授权页面 重写{@link WhitelabelApprovalEndpoint}
     *
     * @param model
     * @return
     */
    @RequestMapping("/oauth/confirm_access")
    public String authorizePage(Map<String, Object> model) {
		AuthorizationRequest authorizationRequest = (AuthorizationRequest) model.get("authorizationRequest");
		final SsoOauthClientDetailsDAO oauthClient = oauthClientService
				.getOne(Wrappers.<SsoOauthClientDetailsDAO>lambdaQuery()
						.eq(SsoOauthClientDetailsDAO::getClientId, authorizationRequest.getClientId()));
		String str = "redirect:{}/confirm_access?clientId={}&scope={}&redirectUri={}&appName={}";
		str = StrUtil.format(str, ssoProperties.getFrontendUrl(),
				authorizationRequest.getClientId(),
				CollUtil.join(authorizationRequest.getScope(), StrUtil.COMMA),
				authorizationRequest.getRedirectUri(),
				oauthClient.getAppName());
		return str;
	}

    /**
     * 自定义错误处理 重写{@link WhitelabelErrorEndpoint}
     *
     * @param request
     * @return
     */
    @RequestMapping("/oauth/error")
    @ResponseBody
    public ResponseEntity<Response> handleError(HttpServletRequest request) {
        Object error = request.getAttribute("error");
        String errorSummary;
        if (error instanceof OAuth2Exception) {
            OAuth2Exception oauthError = (OAuth2Exception) error;
            errorSummary = oauthError.getMessage();
        } else {
            errorSummary = "Unknown error";
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.failure(errorSummary));
    }
}
