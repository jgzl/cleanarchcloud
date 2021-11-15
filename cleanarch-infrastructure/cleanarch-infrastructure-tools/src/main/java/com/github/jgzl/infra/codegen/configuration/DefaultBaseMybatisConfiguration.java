package com.github.jgzl.infra.codegen.configuration;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.github.jgzl.common.data.TenantEnvironment;
import com.github.jgzl.common.data.configuration.BaseMybatisConfiguration;
import com.github.jgzl.common.data.mybatis.auth.DataScopeInnerInterceptor;
import com.github.jgzl.common.data.properties.FrameworkMpProperties;
import com.github.jgzl.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Levin
 */
@Configuration
public class DefaultBaseMybatisConfiguration extends BaseMybatisConfiguration {
    @Bean
	@ConditionalOnMissingBean
    public TenantEnvironment tenantEnvironment() {
        return new TenantEnvironment() {
            @Override
            public Long tenantId() {
                return SecurityUtils.getNonullUserInfo().getTenantId();
            }
            @Override
            public Long userId() {
                return SecurityUtils.getNonullUserInfo().getUserId();
            }
            @Override
            public String realName() {
                return SecurityUtils.getNonullUserInfo().getRealName();
            }
            @Override
            public boolean anonymous() {
                return SecurityUtils.anonymous();
            }
        };
    }
    @Autowired
    private ApplicationContext applicationContext;
    @Override
    protected List<InnerInterceptor> getPaginationBeforeInnerInterceptor() {
        List<InnerInterceptor> list = new ArrayList<>();
        boolean isDataScope = properties.isDataScope();
        if (isDataScope) {
            list.add(new DataScopeInnerInterceptor(applicationContext, tenantEnvironment()));
        }
        return list;
    }
}