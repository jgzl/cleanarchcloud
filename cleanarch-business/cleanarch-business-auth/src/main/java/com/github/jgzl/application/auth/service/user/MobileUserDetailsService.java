package com.github.jgzl.application.auth.service.user;

import com.github.jgzl.application.auth.service.SysUserService;
import com.github.jgzl.common.security.vo.UserVO;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Service;

/**
 *
 * @author lihaifeng
 * 2019/5/14 13:56
 */
@Service
public class MobileUserDetailsService extends AbstractUserDetailService {

    private final SysUserService userService;

	public MobileUserDetailsService(SysUserService userService) {
		this.userService = userService;
	}

	@Override
	protected UserVO getUserVO(final String username) {
		final UserVO user = userService.findUserByMobile(username);
		if (user == null) {
			throw new InternalAuthenticationServiceException("手机号: " + username + ", 不存在");
		}
		return user;
	}
}
