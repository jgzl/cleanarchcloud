package com.gitee.application.auth.bussiness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gitee.application.auth.bussiness.domain.SysUser;
import com.gitee.application.auth.bussiness.dto.UserDTO;

/**
 * @author lihaifeng
 */
public interface SysUserService extends IService<SysUser> {
  /**
   * 通过用户名查找用户信息（用户名唯一主键）
   * @param name
   * @return
   */
  SysUser findUserByName(String name);

  /**
   * 创建新用户
   * @param userDTO
   */
  void saveUser(UserDTO userDTO);
}
