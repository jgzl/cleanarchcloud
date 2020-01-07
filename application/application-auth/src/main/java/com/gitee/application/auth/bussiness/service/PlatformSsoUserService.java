package com.gitee.application.auth.bussiness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gitee.application.auth.bussiness.domain.PlatformSsoUser;
import com.gitee.application.auth.bussiness.dto.UserDTO;

/**
 * @author lihaifeng
 */
public interface PlatformSsoUserService extends IService<PlatformSsoUser> {
  /**
   * 通过用户名查找用户信息（用户名唯一主键）
   * @param name
   * @return
   */
  PlatformSsoUser findUserByName(String name);

  /**
   * 创建新用户
   * @param userDTO
   */
  void saveUser(UserDTO userDTO);
}
