package com.gitee.common.security.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.gitee.common.security.vo.SysUserVO;
import com.gitee.common.security.vo.UserVO;

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
	 * @return MicroservicePlatformUser
	 * <p>
	 */
	public SysUserVO getUser(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof SysUserVO) {
			return (SysUserVO) principal;
		}else if(principal instanceof UserVO){
			UserVO userVO=(UserVO)principal;
			SysUserVO ssoUserVO=new SysUserVO();
			ssoUserVO.setUsername(userVO.getUsername());
			ssoUserVO.setUserId("0");
			return ssoUserVO;
		}else if(principal instanceof User){
			SysUserVO ssoUserVO=new SysUserVO();
			ssoUserVO.setUsername(((User) principal).getUsername());
			ssoUserVO.setUserId("0");
			return ssoUserVO;
		}
		return null;
	}

	/**
	 * 获取用户
	 */
	public SysUserVO getUser() {
		Authentication authentication = getAuthentication();
		return getUser(authentication);
	}
}
