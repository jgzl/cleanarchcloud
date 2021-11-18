package com.github.jgzl.common.boot.base.configuration;

import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.github.jgzl.common.core.util.StringUtils;
import com.github.jgzl.common.data.properties.FrameworkMpProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lihaifeng
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "framework.mybatis-plus.multi-tenant", name = "ds-interceptor", havingValue = "true")
public class DynamicDatasourceWebAutoConfigurer implements WebMvcConfigurer {

    @Resource
    private FrameworkMpProperties properties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HandlerInterceptor() {
            /**
             * 在请求处理之前进行调用（Controller方法调用之前）
             */
            @Override
            public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) {
                String requestUri = request.getRequestURI();
                FrameworkMpProperties.MultiTenant multiTenant = properties.getMultiTenant();
                String tenantCode = request.getHeader(multiTenant.getTenantCodeColumn());
                if (StringUtils.isBlank(tenantCode)) {
                    tenantCode = request.getParameter(multiTenant.getTenantCodeColumn());
                }
                String prefix = multiTenant.getDsPrefix();
                String dsKey = multiTenant.getDefaultDsName();
                if (StringUtils.isNotBlank(tenantCode)) {
                    dsKey = prefix + tenantCode;
                }
                log.info("经过多数据源Interceptor,数据源是{},路径是{}", dsKey, requestUri);
                DynamicDataSourceContextHolder.push(dsKey);
                return true;
            }

            /**
             * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
             */
            @Override
            public void afterCompletion(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler, Exception ex) {
                DynamicDataSourceContextHolder.clear();
            }
        });
    }
}