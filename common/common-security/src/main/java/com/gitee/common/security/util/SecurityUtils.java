package com.gitee.common.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.gitee.common.security.vo.SsoUserVO;

import lombok.experimental.UtilityClass;

/**
 * @author lihaifeng
 */
@UtilityClass
public class SecurityUtils {
	/**
	 * 获取Authentication
	 */
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取用户
	 *
	 * @param authentication
	 * @return PigxUser
	 * <p>
	 */
	public SsoUserVO getUser(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof SsoUserVO) {
			return (SsoUserVO) principal;
		}
		return null;
	}

	/**
	 * 获取用户
	 */
	public SsoUserVO getUser() {
		Authentication authentication = getAuthentication();
		return getUser(authentication);
	}
}
