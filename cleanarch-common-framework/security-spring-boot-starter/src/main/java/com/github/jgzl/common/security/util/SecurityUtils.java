package com.github.jgzl.common.security.util;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.jgzl.common.core.constant.CommonConstants;
import com.github.jgzl.common.security.dataobject.UserInfoDetails;
import com.github.jgzl.common.core.exception.CheckedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

/**
 * @author lihaifeng
 */
public class SecurityUtils {
	/**
	 * 获取Authentication
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取用户详细信息(只适用于 authority 模块)
	 *
	 * @return 结果
	 */
	public static UserInfoDetails getAuthInfo() {
		Authentication authentication = getAuthentication();
		if (authentication instanceof OAuth2Authentication){
			if (anonymous()) {
				throw CheckedException.forbidden("认证信息不存在");
			}
			Authentication userAuthentication = ((OAuth2Authentication )authentication).getUserAuthentication();
			if (userAuthentication.getPrincipal() instanceof UserInfoDetails) {
				return (UserInfoDetails) userAuthentication.getPrincipal();
			}
			String detailsText = JSONUtil.toJsonStr(userAuthentication.getDetails());
			final JSONObject detailJson = JSONUtil.parseObj(detailsText);
			return detailJson.getBean(AUTH_DETAILS_PRINCIPAL, UserInfoDetails.class);
		}else {
			if (anonymous()) {
				return null;
			}
			if (authentication.getPrincipal() instanceof UserInfoDetails) {
				return (UserInfoDetails) authentication.getPrincipal();
			}
			String detailsText = JSONUtil.toJsonStr(authentication.getDetails());
			final JSONObject detailJson = JSONUtil.parseObj(detailsText);
			return detailJson.getBean(AUTH_DETAILS_PRINCIPAL, UserInfoDetails.class);
		}

	}

	public static final String AUTH_DETAILS_PRINCIPAL = "principal";
	public static final String ANONYMOUS_USER = "anonymousUser";

	/**
	 * 是否为匿名用户
	 *
	 * @return 是（true）|不是（false）
	 */
	public static boolean anonymous() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return true;
		}
		if (authentication.getPrincipal() == null || authentication instanceof UsernamePasswordAuthenticationToken) {
			return true;
		}
		return authentication.getPrincipal().equals(ANONYMOUS_USER);
	}

	public static UserInfoDetails getNonullUserInfo() {
		UserInfoDetails authInfo = getAuthInfo();
		if (authInfo!=null) {
			return authInfo;
		}
		authInfo = new UserInfoDetails();
		authInfo.setUserId(CommonConstants.DEFAULT_USER_ID);
		authInfo.setUsername(CommonConstants.DEFAULT_USERNAME);
		authInfo.setNickName(CommonConstants.DEFAULT_NICKNAME);
		authInfo.setRealName(CommonConstants.DEFAULT_NICKNAME);
		authInfo.setTenantId(CommonConstants.DEFAULT_TENANT_ID);
		authInfo.setTenantCode(CommonConstants.DEFAULT_TENANT_CODE);
		authInfo.setEnabled(true);
		return authInfo;
	}
}
