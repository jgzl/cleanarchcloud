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

package com.gitee.application.upms.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.application.upms.mapper.PlatformRoleMapper;
import com.gitee.application.upms.service.IPlatformRoleService;
import com.gitee.common.upms.entity.PlatformRole;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author lihaifeng
 * @since 2020-01-12
 */
@Service
public class PlatformRoleServiceImpl extends ServiceImpl<PlatformRoleMapper, PlatformRole> implements
    IPlatformRoleService {

  @Override
  public List<PlatformRole> findRolesByUserId(Long id) {
    List<PlatformRole> roles = baseMapper.listRolesByUserId(id);
    return roles;
  }
}
