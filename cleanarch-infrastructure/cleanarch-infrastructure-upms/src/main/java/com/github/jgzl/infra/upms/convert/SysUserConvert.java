package com.github.jgzl.infra.upms.convert;

import com.github.jgzl.common.security.vo.SysUserVo;
import com.github.jgzl.common.security.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.github.jgzl.common.security.dataobject.SysUserDo;

/**
 * @author Administrator
 */
@Mapper
public interface SysUserConvert {
	SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

	@Mapping(
			target = "userId", expression = "java(String.valueOf(user.getUserId()))"
	)
    SysUserVo convert(SysUserDo user);

	@Mapping(
			target = "userId", expression = "java(user.getUserId()!=null&&!user.getUserId().equals(\"\")?Long.valueOf(user.getUserId()):null)"
	)
	SysUserDo convert(SysUserVo user);

	UserVo convertUserDetails(SysUserDo user);

	SysUserDo convertUserDetails(UserVo user);
}
