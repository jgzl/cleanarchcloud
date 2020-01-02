package com.gitee.application.auth.bussiness.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.application.auth.bussiness.domain.SysUser;
import com.gitee.application.auth.bussiness.dto.UserDTO;
import com.gitee.application.auth.bussiness.mapper.SysUserMapper;

import cn.hutool.core.bean.BeanUtil;

/**
 * @author lihaifeng
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

  private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

  @Override
  public SysUser findUserByName(String username) {
    return this.getOne(new QueryWrapper<SysUser>().eq("username", username));
  }

  @Override
  public void saveUser(UserDTO userDTO) {
    SysUser sysUser = new SysUser();
    BeanUtil.copyProperties(userDTO, sysUser);
    sysUser.setPassword(ENCODER.encode(sysUser.getPassword()));
    baseMapper.insert(sysUser);
  }
}
