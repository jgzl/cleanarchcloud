package com.github.jgzl.infra.upms.service.impl;

import com.github.jgzl.common.api.vo.UserVo;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.infra.upms.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户信息获取
 *
 * @author lihaifeng
 * @date 2020/7/24 17:06
 */
@Service
@AllArgsConstructor
public class UsernameUserDetailsServiceImpl extends AbstractUserDetailService {

    private final SysUserService userService;

	@Cacheable(value = CacheConstants.USER_DETAILS,key = "#username",unless = "#result==null")
	@Override
	public UserVo getUserVo(final String username) {
		// 查询用户信息,包含角色列表
		UserVo user = userService.findUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("用户名["+username+"]不存在");
		}
		return user;
    }
}
