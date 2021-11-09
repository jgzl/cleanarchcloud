package com.github.jgzl.common.data.mybatis;
import javax.sql.DataSource;
import javax.validation.constraints.NotNull;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.TenantLineInnerInterceptor;
import com.github.jgzl.common.data.external.ReceiveUserInfoService;
import com.github.jgzl.common.data.tenant.TenantHandler;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * xuhang
 */
@Configuration
@ConditionalOnBean(DataSource.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@MapperScan("com.github.jgzl.**.mapper")
public class MybatisPlusConfiguration {

	/**
	 * 新版
	 */
	@Bean
	public MybatisPlusInterceptor mybatisPlusInterceptor(TenantLineHandler tenantHandler) {
		MybatisPlusInterceptor mybatisPlusInterceptor = new MybatisPlusInterceptor();
		// 租户
		mybatisPlusInterceptor.addInnerInterceptor(new TenantLineInnerInterceptor(tenantHandler));
		// 乐观锁
		mybatisPlusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
		// 分页
		mybatisPlusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor());
		return mybatisPlusInterceptor;
	}

	/**
	 * 创建租户维护处理器对象
	 * @return 处理后的租户维护处理器
	 */
	@Bean
	@ConditionalOnMissingBean
	public TenantHandler tenantHandler() {
		return new TenantHandler();
	}

	@Bean
	@ConditionalOnMissingBean
	public BaseDoMetaObjectHandler metaObjectHandler(@NotNull ReceiveUserInfoService receiveUserInfoService) {
		return new BaseDoMetaObjectHandler(receiveUserInfoService);
	}
}