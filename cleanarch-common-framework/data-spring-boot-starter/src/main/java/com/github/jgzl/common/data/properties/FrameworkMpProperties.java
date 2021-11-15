package com.github.jgzl.common.data.properties;

import com.github.jgzl.common.core.constant.CommonConstants;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


/**
 * 客户端认证配置
 *
 * @author Levin
 * @since 2018/11/20
 */
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = FrameworkMpProperties.PREFIX)
public class FrameworkMpProperties {

    public static final String PREFIX = "framework.mybatis-plus";

    /**
     * 攻击 SQL 阻断解析器
     */
    private boolean blockAttack = false;
    /**
     * 是否启用数据权限
     */
    private boolean dataScope = true;
	/**
	 * 是否启用sql性能规范
	 */
    private boolean illegalSql = false;
	/**
	 * 是否启用乐观锁
	 */
	private boolean optimistic = false;

    /**
     * 单页分页条数限制(默认无限制,参见 插件#handlerLimit 方法)
     */
    private long maxLimit = 1000L;

	/**
	 * 租户配置策略
	 */
    private MultiTenant multiTenant = new MultiTenant();

    /**
     * Id生成策略
     */
    private Id id = new Id();


    @Data
    public static class MultiTenant {
        /**
         * 多租户模式
         */
        private MultiTenantType type = MultiTenantType.COLUMN;

        /**
         * 包含租户字段的表
         */
        private List<String> includeTables = Lists.newArrayList();

        /**
         * 多租户数据权限策略
         */
        private MultiTenantStrategy strategy = MultiTenantStrategy.LOCAL;

        /**
         * 在多租户模式为 数据源 时生效
         */
        private String defaultDsName = "master";
        /**
         * 租户id 列名
         */
        private String tenantIdColumn = "tenant_id";
        /**
         * 租户字段列名
         */
        private String tenantCodeColumn = "tenant_code";

        /**
         * 超级租户编码
         */
        private String superTenantCode = CommonConstants.DEFAULT_TENANT_CODE;
        /**
         * 租户数据库前缀
         */
        private String dsPrefix = "tenant_";

        /**
         * 租户SQL脚本
         */
        private List<String> tenantSqlScripts = Lists.newArrayList("classpath*:db/tenant_schema.sql");

        /**
         * 是否从登陆上下文获取数据库信息
         */
        private boolean useTenantContent;
        /**
         * 租户数据源切换拦截器（不建议使用）
         */
        private boolean dsInterceptor;
    }

    @Data
    public static class Id {
        /**
         * 终端ID (0-31)      单机配置0 即可。 集群部署，根据情况每个实例自增即可。
         */
        private Long workerId = 0L;
        /**
         * 数据中心ID (0-31)   单机配置0 即可。 集群部署，根据情况每个实例自增即可。
         */
        private Long dataCenterId = 0L;
    }

}
