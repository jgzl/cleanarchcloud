package com.github.jgzl.application.auth.mvc.provider;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.github.jgzl.application.auth.core.AbstractUserDetailsAuthenticationProvider;
import com.github.jgzl.application.auth.mvc.token.SmsCodeAuthenticationToken;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.data.redis.CustomRedisRepository;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 手机验证码登录系统 Provider
 *
 * @author lihaifeng
 * 2019/7/12 14:35
 * @see DaoAuthenticationProvider
 */
@Slf4j
public class SmsCodeAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private UserDetailsService userDetailsService;

    private CustomRedisRepository redisRepository;

    @Override
    protected Authentication createSuccessAuthentication(final Object principal, final Authentication authentication, final UserDetails user) {
        final SmsCodeAuthenticationToken token = new SmsCodeAuthenticationToken(principal, authentication.getCredentials(), user.getAuthorities());
        token.setDetails(authentication.getDetails());
        return token;
    }

    @Override
    protected void additionalAuthenticationChecks(final UserDetails userDetails, final Authentication authentication) throws
			AuthenticationException {
        if (authentication.getCredentials() == null) {
            log.error("Authentication failed: no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("MobileAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            final String presentedPassword = authentication.getCredentials().toString();
            final Object principal = authentication.getPrincipal();
            final String key = CacheConstants.DEFAULT_CODE_KEY + principal;
            final String code = redisRepository.get(key);
            // 校验验证码
            if (StrUtil.isEmpty(code) || !code.equals(presentedPassword)) {
                log.error("Authentication failed: verifyCode does not match stored value");
                throw new BadCredentialsException(this.messages.getMessage("MobileAuthenticationProvider.badCredentials", "Bad verifyCode"));
            }
            // 校验成功删除验证码(验证码只能使用一次)
            redisRepository.del(key);
        }
    }

    @Override
    protected UserDetails retrieveUser(final String mobile, final Authentication authentication) throws
			AuthenticationException {
        UserDetails loadedUser;
        try {
            loadedUser = userDetailsService.loadUserByUsername(mobile);
        } catch (UsernameNotFoundException var6) {
            throw var6;
        } catch (Exception var7) {
            throw new InternalAuthenticationServiceException(var7.getMessage(), var7);
        }
        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
        } else {
            return loadedUser;
        }
    }

    @Override
    public boolean supports(final Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(final UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public CustomRedisRepository getRedisRepository() {
        return redisRepository;
    }

    public void setRedisRepository(final CustomRedisRepository redisRepository) {
        this.redisRepository = redisRepository;
    }
}
