package com.github.jgzl.infra.upms.service.impl;
import com.github.jgzl.common.api.vo.UserVo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * 抽象UserDetailsService 满足不同的登录方式
 *
 * @author lihaifeng
 * 2019/5/14 11:36
 */
@Slf4j
public abstract class AbstractUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserVo(username);
    }

	/**
	 * 获取 SysUserVo 对象
	 *
	 * @param username 用户名
	 * @return
	 */
	protected abstract UserVo getUserVo(String username);
}
