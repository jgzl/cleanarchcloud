package com.github.jgzl.common.security.component;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.UserAuthenticationConverter;
import org.springframework.util.StringUtils;

/**
 * 重写 UserAuthenticationConverter 在资源服务器获取jwt中的额外信息
 *
 * @author lihaifeng
 * 2019/7/16 11:33
 */
public class CustomUserAuthenticationConverter implements UserAuthenticationConverter {

    private UserDetailsService userDetailsService;

    private Collection<? extends GrantedAuthority> defaultAuthorities;

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public void setDefaultAuthorities(String[] defaultAuthorities) {
        this.defaultAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                .arrayToCommaDelimitedString(defaultAuthorities));
    }

    @Override
    public Map<String, ?> convertUserAuthentication(final Authentication authentication) {
        Map<String, Object> response = new LinkedHashMap<String, Object>();
        response.put(USERNAME, authentication.getName());
        if (authentication.getAuthorities() != null && !authentication.getAuthorities().isEmpty()) {
            response.put(AUTHORITIES, AuthorityUtils.authorityListToSet(authentication.getAuthorities()));
        }
        return response;
    }

    @Override
    public Authentication extractAuthentication(final Map<String, ?> map) {
        if (map.containsKey(USERNAME)) {
            Object principal = map.get(USERNAME);
            Collection<? extends GrantedAuthority> authorities = getAuthorities(map);
            if (userDetailsService != null) {
                UserDetails user = userDetailsService.loadUserByUsername((String) map.get(USERNAME));
                authorities = user.getAuthorities();
                principal = user;
            }
            final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(principal, "N/A", authorities);
            Map<String, Object> info = new HashMap<>(map);
            info.remove(AccessTokenConverter.EXP);
            info.remove(AccessTokenConverter.AUD);
            info.remove(AccessTokenConverter.CLIENT_ID);
            info.remove(AccessTokenConverter.ATI);
            info.remove(AccessTokenConverter.AUTHORITIES);
            info.remove(AccessTokenConverter.SCOPE);
            info.remove(AccessTokenConverter.JTI);
            info.remove(AccessTokenConverter.GRANT_TYPE);
            info.remove(USERNAME);
            authenticationToken.setDetails(info);
            return authenticationToken;
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Map<String, ?> map) {
        if (!map.containsKey(AUTHORITIES)) {
            return defaultAuthorities;
        }
        Object authorities = map.get(AUTHORITIES);
        if (authorities instanceof String) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList((String) authorities);
        }
        if (authorities instanceof Collection) {
            return AuthorityUtils.commaSeparatedStringToAuthorityList(StringUtils
                    .collectionToCommaDelimitedString((Collection<?>) authorities));
        }
        throw new IllegalArgumentException("Authorities must be either a String or a Collection");
    }
}
