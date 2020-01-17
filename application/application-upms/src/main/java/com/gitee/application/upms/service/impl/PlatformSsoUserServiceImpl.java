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

package com.gitee.application.upms.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.application.upms.mapper.PlatformSsoUserMapper;
import com.gitee.application.upms.service.IPlatformRoleService;
import com.gitee.application.upms.service.IPlatformSsoUserService;
import com.gitee.common.core.constant.CacheConstants;
import com.gitee.common.core.util.Result;
import com.gitee.common.upms.dao.PlatformRoleDO;
import com.gitee.common.upms.dao.PlatformSsoUserDO;
import com.gitee.common.upms.dto.UserDTO;
import com.gitee.common.upms.dto.UserInfoDTO;
import com.gitee.common.upms.vo.UserVO;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lihaifeng
 */
@Slf4j
@Service
public class PlatformSsoUserServiceImpl extends ServiceImpl<PlatformSsoUserMapper, PlatformSsoUserDO> implements
    IPlatformSsoUserService {

  private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

  @Autowired
  private IPlatformRoleService roleService;

  /**
   * 通过查用户的全部信息
   * 使用cache相关注解，自动注入数据
   * @param user
   * @return
   */
  @Override
  @Cacheable(value = "findUserInfo", key = "#user.username")
  public UserInfoDTO findUserInfo(PlatformSsoUserDO user) {
    UserInfoDTO userInfoDTO = new UserInfoDTO();
    userInfoDTO.setSysUser(user);
    //设置角色列表  （ID）
    List<Long> roleIds = roleService.findRolesByUserId(user.getId()).stream()
        .map(PlatformRoleDO::getRoleId)
        .collect(Collectors.toList());
    userInfoDTO.setRoles(ArrayUtil.toArray(roleIds, Long.class));
    //设置权限列表（menu.permission）
    return userInfoDTO;
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public Boolean saveUser(UserDTO userDTO) {
    PlatformSsoUserDO platformSsoUser = new PlatformSsoUserDO();
    BeanUtil.copyProperties(userDTO, platformSsoUser);
    LocalDateTime loginTime=platformSsoUser.getLoginTime();
    if (loginTime==null){
      platformSsoUser.setLoginTime(LocalDateTime.now());
    }
    platformSsoUser.setPassword(ENCODER.encode(platformSsoUser.getPassword()));
    this.save(platformSsoUser);
    return true;
  }

  /**
   * 分页查询用户信息（含有角色信息）
   *
   * @param page    分页对象
   * @param userDTO 参数列表
   * @return
   */
  @Override
  public IPage getUsersWithRolePage(Page page, UserDTO userDTO) {
    return baseMapper.getUserVosPage(page, userDTO);
  }

  /**
   * 通过ID查询用户信息
   *
   * @param id 用户ID
   * @return 用户信息
   */
  @Override
  public UserVO selectUserVoById(Long id) {
    return baseMapper.getUserVoById(id);
  }

  /**
   * 删除用户
   *
   * @param sysUser 用户
   * @return Boolean
   */
  @Override
  @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#sysUser.username")
  public Boolean deleteUserById(PlatformSsoUserDO sysUser) {
    this.removeById(sysUser.getId());
    return Boolean.TRUE;
  }

  @Override
  @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
  public Result<Boolean> updateUserInfo(UserDTO userDto) {
    UserVO userVO = baseMapper.getUserVoByUsername(userDto.getUsername());
    PlatformSsoUserDO sysUser = new PlatformSsoUserDO();
    if (StrUtil.isNotBlank(userDto.getPassword())
        && StrUtil.isNotBlank(userDto.getNewPassword())) {
      if (ENCODER.matches(userDto.getPassword(), userVO.getPassword())) {
        sysUser.setPassword(ENCODER.encode(userDto.getNewPassword()));
      } else {
        log.warn("原密码错误，修改密码失败:{}", userDto.getUsername());
        return Result.ok(Boolean.FALSE, "原密码错误，修改失败");
      }
    }
    sysUser.setMobile(userDto.getMobile());
    sysUser.setId(userVO.getId());
    sysUser.setAvatar(userDto.getAvatar());
    return Result.ok(this.updateById(sysUser));
  }

  @Override
  @CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
  public Boolean updateUser(UserDTO userDto) {
    PlatformSsoUserDO sysUser = new PlatformSsoUserDO();
    BeanUtils.copyProperties(userDto, sysUser);

    if (StrUtil.isNotBlank(userDto.getPassword())) {
      sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
    }
    this.updateById(sysUser);
    return Boolean.TRUE;
  }
}
