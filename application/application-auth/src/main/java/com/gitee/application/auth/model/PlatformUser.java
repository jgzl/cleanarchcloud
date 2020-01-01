package com.gitee.application.auth.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

/**
 * @author lihaifeng
 */
public class PlatformUser extends User {

  private Long id;

  public PlatformUser(String username, String password, boolean enabled, boolean accountNonExpired,
      boolean credentialsNonExpired,
      boolean accountNonLocked,
      Collection<? extends GrantedAuthority> authorities) {
    super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
  }

  public Long getId() {
    return id;
  }
}
