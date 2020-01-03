package com.gitee.application.auth.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.gitee.application.auth.bussiness.domain.SysUser;
import com.gitee.application.auth.bussiness.service.SysUserService;
import com.gitee.application.auth.model.PlatformUser;
import com.gitee.common.core.constant.SecurityConstants;

/**
 * @author lihaifeng
 */
@Component
public class PlatformUserDetailsServiceImpl implements PlatformUserDetailsService {

  @Autowired
  private SysUserService sysUserService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    SysUser user = sysUserService.findUserByName(username);
    Set<String> dbAuthsSet = new HashSet<>();
    dbAuthsSet.add(SecurityConstants.ROLE + "ADMIN");
    Collection<? extends GrantedAuthority> authorities
        = AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
    return new PlatformUser(user.getUsername(), SecurityConstants.BCRYPT + user.getPassword(), true,
        true, true, true, authorities);
  }
}
