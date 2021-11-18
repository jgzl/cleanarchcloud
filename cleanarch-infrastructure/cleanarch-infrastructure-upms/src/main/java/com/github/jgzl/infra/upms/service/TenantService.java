package com.github.jgzl.infra.upms.service;
import com.github.jgzl.common.data.mybatis.SuperService;
import com.github.jgzl.infra.upms.domain.entity.tenant.Tenant;
import com.github.jgzl.infra.upms.domain.entity.tenant.TenantConfig;
/**
 * @author lihaifeng
 */
public interface TenantService extends SuperService<Tenant> {

    /**
     * 保存租户
     *
     * @param tenant 租户信息
     */
    void saveOrUpdateTenant(Tenant tenant);
    void tenantConfig(TenantConfig tenantConfig);
    void initSqlScript(Long id);
}
