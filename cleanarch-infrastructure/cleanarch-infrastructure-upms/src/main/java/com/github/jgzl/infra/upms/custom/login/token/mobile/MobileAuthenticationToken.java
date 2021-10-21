package com.github.jgzl.infra.upms.custom.login.token.mobile;

import com.github.jgzl.infra.upms.core.CustomAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 定义验证码登录系统
 *
 * @author lihaifeng
 * 2019/7/12 14:04
 * @see UsernamePasswordAuthenticationToken
 */
public class MobileAuthenticationToken extends CustomAuthenticationToken {

    private static final long serialVersionUID = 6056078576992222156L;

    public MobileAuthenticationToken(final Object principal, final Object credentials) {
        super(principal, credentials);
    }

    public MobileAuthenticationToken(final Object principal, final Object credentials, final Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
