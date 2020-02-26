package com.gitee.application.auth.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gitee.application.auth.service.SsoUserService;
import com.gitee.common.core.constant.CacheConstants;
import com.gitee.common.core.constant.SecurityConstants;
import com.gitee.common.security.vo.SsoUserVO;

/**
 * 用户信息获取
 *
 * @author lihaifeng
 * @date 2018/7/24 17:06
 */
@Service
public class UserNameUserDetailsServiceImpl extends AbstractUserDetailService {

    private final SsoUserService userService;

    private final CacheManager cacheManager;

	public UserNameUserDetailsServiceImpl(SsoUserService userService,
			CacheManager cacheManager) {
		this.userService = userService;
		this.cacheManager = cacheManager;
	}

	@Override
    protected SsoUserVO getUserVO(final String username) {
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		if (cache != null && cache.get(username) != null) {
			return (SsoUserVO) cache.get(username).get();
		}
        // 查询用户信息,包含角色列表
        SsoUserVO user = userService.findUserByUsername(username);
		user.setPassword(SecurityConstants.BCRYPT+user.getPassword());
        if (user == null) {
            throw new UsernameNotFoundException("用户名/密码错误");
        }
        cache.put(username,user);
        return user;
    }

}
