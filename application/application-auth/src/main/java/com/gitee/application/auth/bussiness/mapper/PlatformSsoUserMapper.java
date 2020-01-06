package com.gitee.application.auth.bussiness.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gitee.application.auth.bussiness.domain.PlatformSsoUser;

/**
 * @author lihaifeng
 */
@Mapper
public interface PlatformSsoUserMapper extends BaseMapper<PlatformSsoUser> {

}
