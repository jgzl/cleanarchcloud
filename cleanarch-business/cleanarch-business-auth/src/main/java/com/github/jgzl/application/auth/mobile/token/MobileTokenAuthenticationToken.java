package com.github.jgzl.application.auth.mobile.token;

import com.github.jgzl.application.auth.core.CustomAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 定义手机号获取token 类似与 UsernamePasswordAuthenticationToken
 *
 * @author lihaifeng
 * 2019/5/13 15:20
 * @see UsernamePasswordAuthenticationToken
 */
public class MobileTokenAuthenticationToken extends CustomAuthenticationToken {

    private static final long serialVersionUID = -9192173797915966518L;

    public MobileTokenAuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public MobileTokenAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}
