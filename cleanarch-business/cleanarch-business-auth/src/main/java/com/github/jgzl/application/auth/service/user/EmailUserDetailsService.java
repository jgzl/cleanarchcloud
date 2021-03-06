package com.github.jgzl.application.auth.service.user;

import com.github.jgzl.application.auth.service.SysUserService;
import com.github.jgzl.common.security.vo.UserVo;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Service;

/**
 *
 * @author lihaifeng
 * 2019/5/14 13:56
 */
@Service
public class EmailUserDetailsService extends AbstractUserDetailService {

    private final SysUserService userService;

	public EmailUserDetailsService(SysUserService userService) {
		this.userService = userService;
	}

	@Override
	protected UserVo getUserVo(final String username) {
		final UserVo user = userService.findUserByEmail(username);
		if (user == null) {
			throw new InternalAuthenticationServiceException("邮箱: " + username + ", 不存在");
		}
		return user;
	}
}
