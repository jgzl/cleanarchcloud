package com.github.jgzl.infra.upms.service;

import com.github.jgzl.common.api.dataobject.UserInfoDetails;
import org.springframework.security.core.Authentication;

public interface ThirdUserDetailsService {
	public UserInfoDetails loadUserByUniqueKey(final Authentication authentication);
}
