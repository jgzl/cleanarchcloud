package com.gitee.application.auth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

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
        .and()
        .authorizeRequests()
        .antMatchers("/doc.html", "/swagger-ui.html", "/token/**", "/actuator/**", "/v2/api-docs", "/css/**")
        .permitAll()
        .anyRequest().authenticated()
        .and().csrf().disable();
  }

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    super.configure(resources);
  }
}
