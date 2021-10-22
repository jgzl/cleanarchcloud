package com.github.jgzl.infra.upms.convert;

import com.github.jgzl.common.api.vo.SysUserVo;
import com.github.jgzl.common.api.vo.UserVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.github.jgzl.common.api.dataobject.SysUser;

/**
 * @author lihaifeng
 */
@Mapper
public interface SysUserConvert {
	SysUserConvert INSTANCE = Mappers.getMapper(SysUserConvert.class);

	@Mapping(
			target = "userId", expression = "java(String.valueOf(user.getUserId()))"
	)
    SysUserVo convert(SysUser user);

	@Mapping(
			target = "userId", expression = "java(user.getUserId()!=null&&!user.getUserId().equals(\"\")?Long.valueOf(user.getUserId()):null)"
	)
    SysUser convert(SysUserVo user);

	UserVo convertUserDetails(SysUser user);

	SysUser convertUserDetails(UserVo user);
}
