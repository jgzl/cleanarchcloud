package com.github.jgzl.infra.upms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.jgzl.infra.upms.dataobject.SysSocialUser;
import com.github.jgzl.infra.upms.dataobject.SysSocialUserAuth;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * 社会登录用户管理
 *
 * @author Luckly
 * @date 2019-06-18 17:18:42
 */
@Mapper
public interface SysSocialUserAuthMapper extends BaseMapper<SysSocialUserAuth> {

}
