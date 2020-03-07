package com.gitee.application.auth.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.gitee.common.security.dao.SsoUserDAO;
import com.gitee.common.security.vo.SsoUserVO;
import com.gitee.common.security.vo.UserVO;

/**
 * @author Administrator
 */
@Mapper
public interface SsoUserConvert {
	SsoUserConvert INSTANCE = Mappers.getMapper(SsoUserConvert.class);

	@Mapping(
			target = "userId", expression = "java(String.valueOf(user.getUserId()))"
	)
	SsoUserVO convert(SsoUserDAO user);

	@Mapping(
			target = "userId", expression = "java(user.getUserId()!=null&&!user.getUserId().equals(\"\")?Long.valueOf(user.getUserId()):null)"
	)
	SsoUserDAO convert(SsoUserVO user);

	UserVO convertUserDetails(SsoUserDAO user);

	SsoUserDAO convertUserDetails(UserVO user);
}
