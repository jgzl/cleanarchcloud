package com.github.jgzl.common.data.configuration.dynamic;

import cn.hutool.core.collection.CollectionUtil;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.data.configuration.dynamic.event.body.EventAction;
import com.github.jgzl.common.data.configuration.dynamic.event.body.TenantDynamicDatasource;
import com.github.jgzl.common.data.configuration.dynamic.feign.TenantFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author levin
 */
@Slf4j
@RequiredArgsConstructor
public class TenantDynamicDataSourceLoad {

    private final TenantDynamicDataSourceProcess tenantDynamicDataSourceProcess;
    private final TenantFeignClient tenantFeignClient;

    public void init() {
        log.debug("framework.mybatis-plus.multi-tenant.strategy eq feign , pull dynamic begin...");
        final Result<List<TenantDynamicDatasource>> result = tenantFeignClient.selectAll();
        if (!result.isSuccessful() || CollectionUtil.isEmpty(result.getData())) {
            log.warn("feign pull tenantDynamicDataSources is null......");
            return;
        }
        result.getData().forEach(tenantDynamicDataSource -> tenantDynamicDataSourceProcess.handler(EventAction.ADD, tenantDynamicDataSource));
        log.debug("framework.mybatis-plus.multi-tenant.strategy eq feign , pull dynamic end...");
    }


}
