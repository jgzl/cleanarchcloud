package com.gitee.application.auth.bussiness.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gitee.application.auth.bussiness.domain.SysUser;
import com.gitee.application.auth.bussiness.dto.UserDTO;

/**
 * @author lihaifeng
 */
public interface SysUserService extends IService<SysUser> {
  SysUser findUserByName(String name);

  void saveUser(UserDTO userDTO);
}
