package com.github.jgzl.common.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author lihaifeng
 * @date 2018/8/15
 */
public interface ExtendUserDetailsService extends UserDetailsService {

	/**
	 * 根据社交登录code 登录
	 * @param code TYPE@CODE
	 * @return UserDetails
	 * @throws UsernameNotFoundException
	 */
	UserDetails loadUserBySocial(String code) throws UsernameNotFoundException;

}
