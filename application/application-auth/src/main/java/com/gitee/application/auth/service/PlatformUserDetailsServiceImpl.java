package com.gitee.application.auth.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.gitee.application.auth.bussiness.domain.PlatformSsoUser;
import com.gitee.application.auth.bussiness.service.PlatformSsoUserService;
import com.gitee.application.auth.model.PlatformUser;
import com.gitee.common.core.constant.SecurityConstants;

import lombok.AllArgsConstructor;

/**
 * @author lihaifeng
 */
@Component
@AllArgsConstructor
public class PlatformUserDetailsServiceImpl implements UserDetailsService {

  private final PlatformSsoUserService platformSsoUserService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    PlatformSsoUser user = platformSsoUserService.findUserByName(username);
    Set<String> dbAuthsSet = new HashSet<>();
    dbAuthsSet.add(SecurityConstants.ROLE + "ADMIN");
    Collection<? extends GrantedAuthority> authorities
        = AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
    return new PlatformUser(user.getUsername(), SecurityConstants.BCRYPT + user.getPassword(), true,
        true, true, true, authorities);
  }
}
