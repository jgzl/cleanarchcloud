package com.gitee.application.auth.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.stereotype.Service;

import com.gitee.application.auth.service.SsoUserService;
import com.gitee.common.security.vo.SsoUserVO;

/**
 *
 * @author lihaifeng
 * 2019/5/14 13:56
 */
@Service
public class MobileUserDetailsService extends AbstractUserDetailService {

    private final SsoUserService userService;

	public MobileUserDetailsService(SsoUserService userService) {
		this.userService = userService;
	}

	@Override
    protected SsoUserVO getUserVO(final String username) {
        final SsoUserVO user = userService.findUserByMobile(username);
        if (user == null) {
            throw new InternalAuthenticationServiceException("手机号: " + username + ", 不存在");
        }
        return user;
    }
}
