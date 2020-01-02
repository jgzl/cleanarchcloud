package com.gitee.application.auth.bussiness.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gitee.application.auth.bussiness.domain.SysUser;

/**
 * @author lihaifeng
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
  SysUser selectOne(QueryWrapper<Object> name);
}
