package com.github.jgzl.infra.upms.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.SocialUserAuth;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 社会登录用户管理
 *
 * @author Luckly
 * @date 2019-06-18 17:18:42
 */
@Mapper
public interface SysSocialUserAuthMapper extends BaseMapper<SocialUserAuth> {

}
