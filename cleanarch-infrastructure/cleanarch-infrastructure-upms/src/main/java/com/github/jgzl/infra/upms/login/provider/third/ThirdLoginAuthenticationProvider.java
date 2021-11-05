package com.github.jgzl.infra.upms.login.provider.third;

import com.github.jgzl.infra.upms.core.AbstractUserDetailsAuthenticationProvider;
import com.github.jgzl.infra.upms.login.token.third.ThirdLoginAuthenticationToken;
import com.github.jgzl.infra.upms.service.ThirdUserDetailsService;
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

	private ThirdUserDetailsService userDetailsService;

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
			UserDetails loadedUser = this.getUserDetailsService().loadUserByUniqueKey(authentication);
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

	public void setUserDetailsService(ThirdUserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

	protected ThirdUserDetailsService getUserDetailsService() {
		return this.userDetailsService;
	}
}
