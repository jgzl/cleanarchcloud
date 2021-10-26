package com.github.jgzl.infra.upms.service.impl;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.infra.upms.service.SysUserService;
import com.github.jgzl.common.api.vo.UserVo;
import org.springframework.cache.annotation.Cacheable;
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

	@Cacheable(value = CacheConstants.USER_DETAILS,key = "#username",unless = "#result==null")
	@Override
	public UserVo getUserVo(final String username) {
		final UserVo user = userService.findUserByMobile(username);
		if (user == null) {
			throw new InternalAuthenticationServiceException("手机号[" + username + "]不存在");
		}
		return user;
	}
}
