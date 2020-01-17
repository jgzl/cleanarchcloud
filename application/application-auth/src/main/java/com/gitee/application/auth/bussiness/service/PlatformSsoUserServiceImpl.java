/*
 * Copyright [2020] [lihaifeng,xuhang]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.gitee.application.auth.bussiness.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.application.auth.bussiness.mapper.PlatformSsoUserMapper;
import com.gitee.common.upms.dao.PlatformSsoUserDO;
import com.gitee.common.upms.dto.UserDTO;

import cn.hutool.core.bean.BeanUtil;

/**
 * @author lihaifeng
 */
@Service
public class PlatformSsoUserServiceImpl extends ServiceImpl<PlatformSsoUserMapper, PlatformSsoUserDO> implements
    PlatformSsoUserService {

  private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void saveUser(UserDTO userDTO) {
    PlatformSsoUserDO platformSsoUser = new PlatformSsoUserDO();
    BeanUtil.copyProperties(userDTO, platformSsoUser);
    LocalDateTime loginTime = platformSsoUser.getLoginTime();
    if (loginTime == null) {
      platformSsoUser.setLoginTime(LocalDateTime.now());
    }
    platformSsoUser.setPassword(ENCODER.encode(platformSsoUser.getPassword()));
    this.save(platformSsoUser);
  }
}
