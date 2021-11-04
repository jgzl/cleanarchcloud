package com.github.jgzl.infra.upms.login.provider.third;

import com.github.jgzl.infra.upms.core.AbstractUserDetailsAuthenticationProvider;
import com.github.jgzl.infra.upms.login.token.third.ThirdLoginAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 第三方账户登录系统Provider
 */
@Slf4j
public class ThirdLoginAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	private UserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		log.info("第三方登录认证开始...");
		String username = (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
		String password = (String) authentication.getCredentials();
		ThirdLoginAuthenticationToken result = new ThirdLoginAuthenticationToken(username, password);
		result.setDetails(authentication.getDetails());
		log.info("第三方登录认证成功...");
		return result;
	}

	@Override
	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
		final ThirdLoginAuthenticationToken token = new ThirdLoginAuthenticationToken(principal, authentication.getCredentials(), user.getAuthorities());
		token.setDetails(authentication.getDetails());
		return token;
	}

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, Authentication authentication) throws AuthenticationException {
		log.info("第三方登录不需要对密码不校验");
	}

	@Override
	protected UserDetails retrieveUser(String username, Authentication authentication) throws AuthenticationException {
		try {
			UserDetails loadedUser = this.getUserDetailsService().loadUserByUsername(username);
			if (loadedUser == null) {
				throw new InternalAuthenticationServiceException(
						"UserDetailsService returned null, which is an interface contract violation");
			}
			return loadedUser;
		}
		catch (UsernameNotFoundException | InternalAuthenticationServiceException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
		}
	}

	@Override
	public boolean supports(Class<?> aClass) {
		return ThirdLoginAuthenticationToken.class.isAssignableFrom(aClass);
	}

	public void setUserDetailsService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	protected UserDetailsService getUserDetailsService() {
		return this.userDetailsService;
	}
}
