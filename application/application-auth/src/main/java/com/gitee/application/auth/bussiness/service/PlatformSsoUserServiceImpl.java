package com.gitee.application.auth.bussiness.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.application.auth.bussiness.domain.PlatformSsoUser;
import com.gitee.application.auth.bussiness.dto.UserDTO;
import com.gitee.application.auth.bussiness.mapper.PlatformSsoUserMapper;

import cn.hutool.core.bean.BeanUtil;

/**
 * @author lihaifeng
 */
@Service
public class PlatformSsoUserServiceImpl extends ServiceImpl<PlatformSsoUserMapper, PlatformSsoUser> implements PlatformSsoUserService {

  private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

  @Override
  public PlatformSsoUser findUserByName(String username) {
    return this.getOne(Wrappers.<PlatformSsoUser>lambdaQuery().eq(PlatformSsoUser::getUsername,username));
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void saveUser(UserDTO userDTO) {
    PlatformSsoUser platformSsoUser = new PlatformSsoUser();
    BeanUtil.copyProperties(userDTO, platformSsoUser);
    platformSsoUser.setPassword(ENCODER.encode(platformSsoUser.getPassword()));
    baseMapper.insert(platformSsoUser);
  }
}
