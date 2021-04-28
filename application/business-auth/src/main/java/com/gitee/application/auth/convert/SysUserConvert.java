package com.gitee.application.auth.convert;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.gitee.common.security.dataobject.SysUserDO;
import com.gitee.common.security.vo.SysUserVO;
import com.gitee.common.security.vo.UserVO;

/**
 * @author Administrator
 */
@Mapper
public interface SysUserConvert {
	SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

	@Mapping(
			target = "userId", expression = "java(String.valueOf(user.getUserId()))"
	)
	SysUserVO convert(SysUserDO user);

	@Mapping(
			target = "userId", expression = "java(user.getUserId()!=null&&!user.getUserId().equals(\"\")?Long.valueOf(user.getUserId()):null)"
	)
	SysUserDO convert(SysUserVO user);

	UserVO convertUserDetails(SysUserDO user);

	SysUserDO convertUserDetails(UserVO user);
}
