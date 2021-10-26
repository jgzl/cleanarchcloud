package com.github.jgzl.infra.upms.login.provider.email;
import com.github.jgzl.infra.upms.core.AbstractUserDetailsAuthenticationProvider;
import com.github.jgzl.infra.upms.login.token.email.EmailTokenAuthenticationToken;
import com.github.jgzl.common.cache.support.CustomRedisRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.github.jgzl.common.core.constant.CacheConstants;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 邮箱验证码登录系统 Provider
 *
 * @author lihaifeng
 * 2019/5/13 15:25
 * @see DaoAuthenticationProvider
 */
@Slf4j
public class EmailTokenAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private UserDetailsService userDetailsService;

    private CustomRedisRepository redisRepository;

    @Override
    protected Authentication createSuccessAuthentication(final Object principal, final Authentication authentication, final UserDetails user) {
        final EmailTokenAuthenticationToken token = new EmailTokenAuthenticationToken(principal, authentication.getCredentials(), user.getAuthorities());
        token.setDetails(authentication.getDetails());
        return token;
    }

    @Override
    protected void additionalAuthenticationChecks(final UserDetails userDetails, final Authentication authentication) throws
			AuthenticationException {
        if (authentication.getCredentials() == null) {
            log.error("Authentication failed: no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("EmailAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
			log.info("邮箱登录认证开始...");
            final String presentedPassword = authentication.getCredentials().toString();
            final Object principal = authentication.getPrincipal();
            final String key = CacheConstants.DEFAULT_CODE_KEY + principal;
            final String code = redisRepository.get(key);
            // 校验验证码
            if (StrUtil.isEmpty(code) || !code.equals(presentedPassword)) {
                log.error("Authentication failed: verifyCode does not match stored value");
                throw new BadCredentialsException(this.messages.getMessage("EmailAuthenticationProvider.badCredentials", "Bad verifyCode"));
            }
            // 校验成功删除验证码(验证码只能使用一次)
            redisRepository.del(key);
			log.info("邮箱登录认证成功...");
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
        return EmailTokenAuthenticationToken.class.isAssignableFrom(authentication);
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
