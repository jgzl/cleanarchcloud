package com.github.jgzl.application.auth.service.user;

import com.github.jgzl.application.auth.service.SysUserService;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.security.vo.UserVo;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户信息获取
 *
 * @author lihaifeng
 * @date 2018/7/24 17:06
 */
@Service
public class UserNameUserDetailsServiceImpl extends AbstractUserDetailService {

    private final SysUserService userService;

    private final CacheManager cacheManager;

	public UserNameUserDetailsServiceImpl(SysUserService userService,
			CacheManager cacheManager) {
		this.userService = userService;
		this.cacheManager = cacheManager;
	}

	@Override
	protected UserVo getUserVo(final String username) {
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		if (cache != null && cache.get(username) != null) {
			return (UserVo) cache.get(username).get();
		}
		// 查询用户信息,包含角色列表
		UserVo user = userService.findUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("用户名/密码错误");
		}
		cache.put(username, user);
        return user;
    }

}
