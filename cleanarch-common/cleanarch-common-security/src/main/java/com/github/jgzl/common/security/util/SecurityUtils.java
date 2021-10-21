package com.github.jgzl.common.security.util;

import com.github.jgzl.common.data.external.ReceiveUserInfoService;
import com.github.jgzl.common.security.vo.SysUserVo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import com.github.jgzl.common.security.vo.UserVo;

import org.springframework.stereotype.Component;

/**
 * @author lihaifeng
 */
@Component
public class SecurityUtils implements ReceiveUserInfoService {
	/**
	 * 获取Authentication
	 */
	public static Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

	/**
	 * 获取用户
	 *
	 * @param authentication
	 * @return MicroservicePlatformUser
	 * <p>
	 */
	public static SysUserVo getUser(Authentication authentication) {
		Object principal = authentication.getPrincipal();
		if (principal instanceof SysUserVo) {
			return (SysUserVo) principal;
		}else if(principal instanceof UserVo){
			UserVo userVo=(UserVo)principal;
			SysUserVo ssoUserVo=new SysUserVo();
			ssoUserVo.setUsername(userVo.getUsername());
			ssoUserVo.setUserId("0");
			return ssoUserVo;
		}else if(principal instanceof User){
			SysUserVo ssoUserVo=new SysUserVo();
			ssoUserVo.setUsername(((User) principal).getUsername());
			ssoUserVo.setUserId("0");
			return ssoUserVo;
		}
		return null;
	}

	/**
	 * 获取用户
	 */
	public static SysUserVo getUser() {
		Authentication authentication = getAuthentication();
		return getUser(authentication);
	}

	@Override
	public SysUserVo getCurrentUserAccount() {
		return getUser();
	}
}
