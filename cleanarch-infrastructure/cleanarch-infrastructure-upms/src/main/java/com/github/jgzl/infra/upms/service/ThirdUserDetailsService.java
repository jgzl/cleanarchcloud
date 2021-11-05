package com.github.jgzl.infra.upms.service;

import com.github.jgzl.common.api.vo.UserVo;
import org.springframework.security.core.Authentication;

public interface ThirdUserDetailsService {
	public UserVo loadUserByUniqueKey(final Authentication authentication);
}
