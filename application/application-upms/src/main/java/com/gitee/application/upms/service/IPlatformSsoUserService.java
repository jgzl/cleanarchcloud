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

package com.gitee.application.upms.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gitee.common.core.util.Result;
import com.gitee.common.upms.dao.PlatformSsoUserDO;
import com.gitee.common.upms.dto.UserDTO;
import com.gitee.common.upms.dto.UserInfoDTO;
import com.gitee.common.upms.vo.UserVO;

/**
 * <p>
 * 单点登录用户表  服务类
 * </p>
 *
 * @author lihaifeng
 * @since 2020-01-12
 */
public interface IPlatformSsoUserService extends IService<PlatformSsoUserDO> {
  /**
   * 通过用户名查找用户信息（用户名唯一主键）
   * @param platformSsoUser
   * @return
   */
  UserInfoDTO findUserInfo(PlatformSsoUserDO platformSsoUser);

  /**
   * 分页查询用户信息（含有角色信息）
   *
   * @param page    分页对象
   * @param userDTO 参数列表
   * @return
   */
  IPage getUsersWithRolePage(Page page, UserDTO userDTO);

  /**
   * 创建新用户
   * @param userDTO
   * @return Boolean
   */
  Boolean saveUser(UserDTO userDTO);

  /**
   * 删除用户
   *
   * @param sysUser 用户
   * @return boolean
   */
  Boolean deleteUserById(PlatformSsoUserDO sysUser);

  /**
   * 更新当前用户基本信息
   *
   * @param userDto 用户信息
   * @return Boolean
   */
  Result<Boolean> updateUserInfo(UserDTO userDto);

  /**
   * 更新指定用户信息
   *
   * @param userDto 用户信息
   * @return
   */
  Boolean updateUser(UserDTO userDto);

  /**
   * 通过ID查询用户信息
   *
   * @param id 用户ID
   * @return 用户信息
   */
  UserVO selectUserVoById(Long id);
}
