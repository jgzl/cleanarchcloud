package com.github.jgzl.common.security.annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.github.jgzl.common.security.component.ExtendResourceServerAutoConfiguration;
import com.github.jgzl.common.security.component.ExtendSecurityBeanDefinitionRegistrar;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * @author lihaifeng
 * @date 2020/11/10
 * 资源服务注解
 */
@Documented
@Inherited
@EnableResourceServer
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@EnableGlobalMethodSecurity(prePostEnabled = true)
@Import({ExtendResourceServerAutoConfiguration.class, ExtendSecurityBeanDefinitionRegistrar.class})
public @interface EnableResourceServerPlus {

}
