package com.gitee.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

import com.gitee.config.SwaggerAutoConfiguration;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;

/**
 * @author lihaifeng
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SwaggerAutoConfiguration.class, BeanValidatorPluginsConfiguration.class})
public @interface EnableSwaggerPlus {
}
