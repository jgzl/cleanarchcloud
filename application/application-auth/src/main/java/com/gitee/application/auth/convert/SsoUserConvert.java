package com.gitee.application.auth.convert;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.gitee.common.security.dao.SsoUserDAO;
import com.gitee.common.security.vo.SsoUserVO;

/**
 * @author Administrator
 */
@Mapper
public interface SsoUserConvert {
	public SsoUserConvert INSTANCE= Mappers.getMapper(SsoUserConvert.class);
	SsoUserVO convert(SsoUserDAO dao);
}
