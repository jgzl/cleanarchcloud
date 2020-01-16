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

package com.gitee.common.security.login;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.gitee.common.core.constant.CacheConstants;
import com.gitee.common.core.constant.SecurityConstants;
import com.gitee.common.core.util.Result;
import com.gitee.common.upms.dao.PlatformSsoUserDAO;
import com.gitee.common.upms.dto.UserInfoDTO;
import com.gitee.common.upms.feign.RemoteUserService;

import lombok.AllArgsConstructor;

/**
 * @author lihaifeng
 */
@Component
@AllArgsConstructor
public class PlatformUserDetailsServiceImpl implements UserDetailsService {

  private final RemoteUserService remoteUserService;

  private final CacheManager cacheManager;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
    if (cache != null && cache.get(username) != null) {
      return (PlatformUser) cache.get(username).get();
    }
    Result<UserInfoDTO> userInfoDTO = remoteUserService.info(username);
    PlatformSsoUserDAO user = userInfoDTO.getData().getSysUser();
    Set<String> authSet = new HashSet<>();
    authSet.add(SecurityConstants.ROLE + "ADMIN");
    Collection<? extends GrantedAuthority> authorities
        = AuthorityUtils.createAuthorityList(authSet.toArray(new String[0]));
    UserDetails userDetails= new PlatformUser(user.getId(),user.getUsername(), SecurityConstants.BCRYPT + user.getPassword(), true,
        true, true, true, authorities);
    cache.put(username, userDetails);
    return userDetails;
  }
}
