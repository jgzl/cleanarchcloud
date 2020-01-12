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

package com.gitee.application.upms.controller;

import javax.validation.Valid;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gitee.application.upms.service.IPlatformSsoUserService;
import com.gitee.common.core.util.Result;
import com.gitee.common.security.util.SecurityUtils;
import com.gitee.common.upms.dto.UserDTO;
import com.gitee.common.upms.entity.PlatformSsoUser;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * @author lihaifeng
 * @date 2018/12/16
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Api(value = "user", tags = "用户管理模块")
public class PlatformSsoUserController {
  private final IPlatformSsoUserService userService;

  /**
   * 获取当前用户全部信息
   *
   * @return 用户信息
   */
  @GetMapping(value = {"/current"})
  public Result info() {
    String username = SecurityUtils.getUser().getUsername();
    PlatformSsoUser user = userService.getOne(Wrappers.<PlatformSsoUser>query()
        .lambda().eq(PlatformSsoUser::getUsername, username));
    if (user == null) {
      return Result.failed(null, "获取当前用户信息失败");
    }
    return Result.ok(userService.findUserInfo(user));
  }

  /**
   * 获取指定用户全部信息
   *
   * @return 用户信息
   */
  @GetMapping("/info/{username}")
  public Result info(@PathVariable String username) {
    PlatformSsoUser user = userService.getOne(Wrappers.<PlatformSsoUser>query()
        .lambda().eq(PlatformSsoUser::getUsername, username));
    if (user == null) {
      return Result.failed(null, String.format("用户信息为空 %s", username));
    }
    return Result.ok(userService.findUserInfo(user));
  }

  /**
   * 通过ID查询用户信息
   *
   * @param id ID
   * @return 用户信息
   */
  @GetMapping("/{id}")
  public Result user(@PathVariable Long id) {
    return Result.ok(userService.selectUserVoById(id));
  }

  /**
   * 根据用户名查询用户信息
   *
   * @param username 用户名
   * @return
   */
  @GetMapping("/details/{username}")
  public Result user(@PathVariable String username) {
    PlatformSsoUser condition = new PlatformSsoUser();
    condition.setUsername(username);
    return Result.ok(userService.getOne(new QueryWrapper<>(condition)));
  }

  /**
   * 删除用户信息
   *
   * @param id ID
   * @return R
   */
  @DeleteMapping("/{id}")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  @ApiOperation(value = "删除用户", notes = "根据ID删除用户")
  @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "int", paramType = "path")
  public Result userDel(@PathVariable Integer id) {
    PlatformSsoUser PlatformSsoUser = userService.getById(id);
    return Result.ok(userService.deleteUserById(PlatformSsoUser));
  }

  /**
   * 添加用户
   *
   * @param userDto 用户信息
   * @return success/false
   */
  @PostMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Result user(@RequestBody UserDTO userDto) {
    return Result.ok(userService.saveUser(userDto));
  }

  /**
   * 更新用户信息
   *
   * @param userDto 用户信息
   * @return R
   */
  @PutMapping
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public Result updateUser(@Valid @RequestBody UserDTO userDto) {
    return Result.ok(userService.updateUser(userDto));
  }

  /**
   * 分页查询用户
   *
   * @param page    参数集
   * @param userDTO 查询参数列表
   * @return 用户集合
   */
  @GetMapping("/page")
  public Result getUserPage(Page page, UserDTO userDTO) {
    return Result.ok(userService.getUsersWithRolePage(page, userDTO));
  }

  /**
   * 修改个人信息
   *
   * @param userDto userDto
   * @return success/false
   */
  @PutMapping("/edit")
  public Result updateUserInfo(@Valid @RequestBody UserDTO userDto) {
    return userService.updateUserInfo(userDto);
  }

}