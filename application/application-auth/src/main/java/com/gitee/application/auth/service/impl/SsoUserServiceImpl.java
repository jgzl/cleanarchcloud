package com.gitee.application.auth.service.impl;

import java.util.Collections;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.application.auth.convert.SsoUserConvert;
import com.gitee.application.auth.mapper.SsoUserMapper;
import com.gitee.application.auth.service.SsoUserService;
import com.gitee.common.core.constant.CommonConstants;
import com.gitee.common.security.dao.SsoUserDAO;
import com.gitee.common.security.vo.Operation;
import com.gitee.common.security.vo.SsoRoleVO;
import com.gitee.common.security.vo.SsoUserVO;

/**
 * @author Administrator
 */
@Service
public class SsoUserServiceImpl extends ServiceImpl<SsoUserMapper, SsoUserDAO> implements SsoUserService {

	@Override
	public SsoUserVO findUserByUsername(String username) {
		SsoUserDAO dao = this.baseMapper
				.selectOne(Wrappers.<SsoUserDAO>lambdaQuery().eq(SsoUserDAO::getUsername, username));
		SsoUserVO user = SsoUserConvert.INSTANCE.convert(dao);
		user.setEnabled(true);
		user.setExpired(false);
		user.setLocked(false);
		user.setPasswordExpired(false);
		user.setRoles(Collections.singletonList(defaultRole()));
		return user;
	}

	@Override
	public SsoUserVO findUserByMobile(String mobile) {
		SsoUserDAO dao = this.baseMapper
				.selectOne(Wrappers.<SsoUserDAO>lambdaQuery().eq(SsoUserDAO::getMobile, mobile));
		SsoUserVO user = SsoUserConvert.INSTANCE.convert(dao);
		user.setEnabled(true);
		user.setExpired(false);
		user.setLocked(false);
		user.setPasswordExpired(false);
		user.setRoles(Collections.singletonList(defaultRole()));
		return user;
	}

	private SsoRoleVO defaultRole() {
		return new SsoRoleVO().setRemark(CommonConstants.ROLE_DEFAULT).setOperations( Collections.singletonList(new Operation(CommonConstants.OP_DEFAULT)));
	}
}
