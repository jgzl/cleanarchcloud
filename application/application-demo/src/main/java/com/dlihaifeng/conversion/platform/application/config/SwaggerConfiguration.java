package com.dlihaifeng.conversion.platform.application.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.Order;

import com.github.xiaoymin.knife4j.spring.annotations.EnableSwaggerBootstrapUi;
import com.google.common.collect.Lists;

import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * @author lihaifeng
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUi
@Import(BeanValidatorPluginsConfiguration.class)
public class SwaggerConfiguration {

  @Bean(value = "orderApi")
  @Order(value = 1)
  public Docket groupRestApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(groupApiInfo())
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.dlihaifeng.conversion.platform.application.controller"))
        .paths(PathSelectors.any())
        .build()
        .securityContexts(Lists.newArrayList(securityContext(), securityContext1()))
        .securitySchemes(Lists.newArrayList(apiKey(), apiKey1()));
  }

  private ApiInfo groupApiInfo() {
    return new ApiInfoBuilder()
        .title("demo-swagger-bootstrap-ui")
        .description("<div style='font-size:14px;color:red;'>demo-swagger-bootstrap-ui</div>")
        .termsOfServiceUrl("http://www.group.com/")
        .contact(new Contact("李海峰", "http://demo-service", "li7hai26@gmail.com"))
        .version("2.0")
        .build();
  }


  private ApiKey apiKey() {
    return new ApiKey("BearerToken", "Authorization", "header");
  }

  private ApiKey apiKey1() {
    return new ApiKey("BearerToken1", "Authorization-x", "header");
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder()
        .securityReferences(defaultAuth())
        .forPaths(PathSelectors.regex("/.*"))
        .build();
  }

  private SecurityContext securityContext1() {
    return SecurityContext.builder()
        .securityReferences(defaultAuth1())
        .forPaths(PathSelectors.regex("/.*"))
        .build();
  }

  List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Lists.newArrayList(new SecurityReference("BearerToken", authorizationScopes));
  }

  List<SecurityReference> defaultAuth1() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Lists.newArrayList(new SecurityReference("BearerToken1", authorizationScopes));
  }
}
