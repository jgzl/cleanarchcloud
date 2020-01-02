package com.gitee.application.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.gitee.application.auth.handler.FormAuthenticationFailureHandler;

/**
 * @author lihaifeng
 */
@Configuration
@EnableResourceServer
public class PlatformResourceServerConfiguration extends ResourceServerConfigurerAdapter {

  @Override
  public void configure(HttpSecurity http) throws Exception {
    http
        .headers().frameOptions().disable()
        .and()
        .formLogin()
        .loginPage("/token/login")
        .loginProcessingUrl("/token/form")
        .failureHandler(failureHandler())
        .and()
        .authorizeRequests()
        .antMatchers("/doc.html", "/swagger-ui.html", "/v2/api-docs", "/token/**", "/actuator/**", "/css/**",
            "/oauth/**")
        .permitAll()
        .anyRequest().authenticated()
        .and().csrf().disable();
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    super.configure(resources);
  }

  @Bean
  public AuthenticationFailureHandler failureHandler() {
    return new FormAuthenticationFailureHandler();
  }
}
