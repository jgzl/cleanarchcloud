package com.github.jgzl.application.auth.service;

import java.util.Collections;

import com.github.jgzl.common.core.constant.CommonConstants;
import com.github.jgzl.common.security.vo.Operation;
import com.github.jgzl.common.security.vo.SysRoleVo;
import com.github.jgzl.common.security.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cn.hutool.core.util.RandomUtil;

/**
 * 模拟 UserService 实现查询用户
 * 在实际使用上需要替换
 *
 * @author lihaifeng
 * 2019/7/3 9:52
 */
@Service
public class MockUserService {

    @Autowired
    private PasswordEncoder encoder;

    private static final String PWD = "$2a$10$X8qJKHLTM9MjCVv9JE.dNOkqzuXkLfJ5kdt45x9AG.7aFby8JLdAC";

	/**
	 * 根据用户名称返回用户
	 *
	 * @param username 用户名称,必须唯一
	 * @return
	 */
	public UserVo findUserByUsername(String username) {
		final UserVo user = new UserVo();
		user.setUsername(username);
		// 密码和用户名保持一致
		user.setPassword(PWD);
		user.setEnabled(true);
		user.setUserId(RandomUtil.randomLong());
		user.setEnabled(true);
		user.setExpired(false);
		user.setLocked(false);
		user.setPasswordExpired(false);
		user.setRoles(Collections.singletonList(defaultRole()));
        return user;
    }

	/**
	 * 根据手机号返回用户
	 *
	 * @param mobile 手机号,必须唯一
	 * @return
	 */
	public UserVo findUserByMobile(String mobile) {
		final UserVo user = new UserVo();
		user.setUsername(mobile);
		// 密码和用户名保持一致
		user.setPassword(encoder.encode(mobile));
		user.setEnabled(true);
		user.setUserId(RandomUtil.randomLong());
		user.setEnabled(true);
		user.setExpired(false);
		user.setLocked(false);
		user.setPasswordExpired(false);
		user.setRoles(Collections.singletonList(defaultRole()));
        return user;
    }

    private SysRoleVo defaultRole() {
		return new SysRoleVo().setRemark(
				CommonConstants.ROLE_DEFAULT)
				.setOperations(Collections.singletonList(new Operation(CommonConstants.OP_DEFAULT)));
	}
}
