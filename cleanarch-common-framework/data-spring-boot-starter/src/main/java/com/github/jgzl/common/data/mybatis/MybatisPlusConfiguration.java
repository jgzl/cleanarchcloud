package com.github.jgzl.common.data.mybatis;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.github.jgzl.common.data.config.MybatisProperties;
import com.github.jgzl.common.data.datascope.DataScopeHandle;
import com.github.jgzl.common.data.datascope.DataScopeInnerInterceptor;
import com.github.jgzl.common.data.datascope.DataScopeSqlInjector;
import com.github.jgzl.common.data.datascope.ExtendDefaultDatascopeHandle;
import com.github.jgzl.common.data.resolver.SqlFilterArgumentResolver;
import com.github.jgzl.common.data.tenant.ExtendTenantHandler;
import com.github.jgzl.common.security.service.ExtendUser;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.List;

/**
 * @author lihaifeng
 * @date 2020-02-08
 */
@Configuration
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@EnableConfigurationProperties(MybatisProperties.class)
public class MybatisPlusConfiguration implements WebMvcConfigurer {

	/**
	 * 增加请求参数解析器，对请求中的参数注入SQL 检查
	 * @param resolverList
	 */
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolverList) {
		resolverList.add(new SqlFilterArgumentResolver());
	}

	/**
	 * pigx 默认数据权限处理器
	 * @return PigxDefaultDatascopeHandle
	 */
	@Bean
	@ConditionalOnMissingBean
	@ConditionalOnClass(ExtendUser.class)
	public DataScopeHandle dataScopeHandle() {
		return new ExtendDefaultDatascopeHandle();
	}

	/**
	 * mybatis plus 拦截器配置
	 * @return PigxDefaultDatascopeHandle
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor() {
		MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
		// 多租户支持
		TenantLineInnerInterceptor tenantLineInnerInterceptor = new TenantLineInnerInterceptor();
		tenantLineInnerInterceptor.setTenantLineHandler(pigxTenantHandler());
		interceptor.addInnerInterceptor(tenantLineInnerInterceptor);
		// 数据权限
		DataScopeInnerInterceptor dataScopeInnerInterceptor = new DataScopeInnerInterceptor();
		dataScopeInnerInterceptor.setDataScopeHandle(dataScopeHandle());
		interceptor.addInnerInterceptor(dataScopeInnerInterceptor);
		// 分页支持
		PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor();
		paginationInnerInterceptor.setDbType(DbType.MYSQL);
		paginationInnerInterceptor.setMaxLimit(1000L);
		interceptor.addInnerInterceptor(paginationInnerInterceptor);
		return interceptor;
	}

	/**
	 * 创建租户维护处理器对象
	 * @return 处理后的租户维护处理器
	 */
	@Bean
	@ConditionalOnMissingBean
	public ExtendTenantHandler pigxTenantHandler() {
		return new ExtendTenantHandler();
	}

	/**
	 * 扩展 mybatis-plus baseMapper 支持数据权限
	 * @return
	 */
	@Bean
	@ConditionalOnBean(DataScopeHandle.class)
	public DataScopeSqlInjector dataScopeSqlInjector() {
		return new DataScopeSqlInjector();
	}

	/**
	 * SQL 日志格式化
	 * @return DruidSqlLogFilter
	 */
	@Bean
	public DruidSqlLogFilter sqlLogFilter(MybatisProperties properties) {
		return new DruidSqlLogFilter(properties);
	}

}
