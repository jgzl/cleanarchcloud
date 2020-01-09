/*
 *    Copyright [2020] [lihaifeng,xuhang]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gitee.application.upms.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gitee.common.upms.dto.UserDTO;
import com.gitee.common.upms.entity.PlatformSsoUser;
import com.gitee.common.upms.vo.UserVO;

/**
 * @author lihaifeng
 */
public interface PlatformSsoUserMapper extends BaseMapper<PlatformSsoUser> {
  /**
   * 通过用户名查询用户信息（含有角色信息）
   *
   * @param username 用户名
   * @return userVo
   */
  UserVO getUserVoByUsername(String username);

  /**
   * 分页查询用户信息（含角色）
   *
   * @param page      分页
   * @param userDTO   查询参数
   * @return list
   */
  IPage<List<UserVO>> getUserVosPage(Page page, @Param("query") UserDTO userDTO);

  /**
   * 通过ID查询用户信息
   *
   * @param id 用户ID
   * @return userVo
   */
  UserVO getUserVoById(Long id);
}