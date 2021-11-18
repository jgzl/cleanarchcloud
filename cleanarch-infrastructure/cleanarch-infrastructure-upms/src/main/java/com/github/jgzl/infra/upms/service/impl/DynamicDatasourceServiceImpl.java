package com.github.jgzl.infra.upms.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.github.jgzl.common.core.exception.CheckedException;
import com.github.jgzl.common.data.configuration.dynamic.event.DynamicDatasourceEvent;
import com.github.jgzl.common.data.configuration.dynamic.event.body.EventAction;
import com.github.jgzl.common.data.configuration.dynamic.event.body.TenantDynamicDatasource;
import com.github.jgzl.common.data.mybatis.SuperServiceImpl;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.common.data.mybatis.conditions.query.LbqWrapper;
import com.github.jgzl.infra.upms.domain.entity.tenant.DynamicDatasource;
import com.github.jgzl.infra.upms.domain.vo.TenantDynamicDatasourceVO;
import com.github.jgzl.infra.upms.repository.DynamicDatasourceMapper;
import com.github.jgzl.infra.upms.repository.TenantConfigMapper;
import com.github.jgzl.infra.upms.service.DynamicDatasourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.github.jgzl.infra.upms.domain.converts.TenantDynamicDatasourceConverts.TENANT_DYNAMIC_DATASOURCE_VO_2_TENANT_DYNAMIC_DATASOURCE_CONVERTS;

/**
 * @author lihaifeng
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DynamicDatasourceServiceImpl extends SuperServiceImpl<DynamicDatasourceMapper, DynamicDatasource> implements DynamicDatasourceService {

    private final TenantConfigMapper tenantConfigMapper;
    private final ApplicationEventPublisher eventPublisher;
    private final ApplicationContext applicationContext;

    @Override
    public List<TenantDynamicDatasourceVO> selectTenantDynamicDatasource() {
        return this.tenantConfigMapper.selectTenantDynamicDatasource(null);
    }

    @Override
    public void ping(Long id) {
        log.debug("查询结果 - {}", JSON.toJSONString(""));
    }

    @PostConstruct
    public void init() {
        final List<TenantDynamicDatasourceVO> dataSourceList = this.tenantConfigMapper.selectTenantDynamicDatasource(null);
        if (CollectionUtil.isEmpty(dataSourceList)) {
            log.warn("未找到符合条件的数据源...");
            return;
        }
        for (TenantDynamicDatasourceVO dynamicDatasource : dataSourceList) {
            publishEvent(EventAction.ADD, dynamicDatasource);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateDatabase(DynamicDatasource dynamicDatasource) {
        final LbqWrapper<DynamicDatasource> lbqWrapper = Wraps.<DynamicDatasource>lbQ().eq(DynamicDatasource::getPoolName, dynamicDatasource.getPoolName());
        if (dynamicDatasource.getId() != null) {
            lbqWrapper.ne(DynamicDatasource::getId, dynamicDatasource.getId());
        }
        final long count = super.count(lbqWrapper);
        if (count > 0) {
            throw CheckedException.badRequest("连接池名称已存在");
        }
        super.saveOrUpdate(dynamicDatasource);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeDatabaseById(Long id) {
        Optional.ofNullable(this.baseMapper.selectById(id)).orElseThrow(() -> CheckedException.notFound("数据连接信息不存在"));
        this.baseMapper.deleteById(id);
        final List<TenantDynamicDatasourceVO> dataSourceList = this.tenantConfigMapper.selectTenantDynamicDatasource(id);
        for (TenantDynamicDatasourceVO tenantDynamicDatasource : dataSourceList) {
            publishEvent(EventAction.DEL, tenantDynamicDatasource);
        }
    }

    @Override
    public void publishEvent(EventAction action, Long tenantId) {
        final TenantDynamicDatasourceVO dynamicDatasource = this.tenantConfigMapper.getTenantDynamicDatasourceByTenantId(tenantId);
        publishEvent(action, dynamicDatasource);
    }

    private void publishEvent(EventAction action, TenantDynamicDatasourceVO dynamicDatasource) {
        if (Objects.isNull(dynamicDatasource)) {
            throw CheckedException.notFound("租户未关联数据源信息");
        }
        final TenantDynamicDatasource datasource = TENANT_DYNAMIC_DATASOURCE_VO_2_TENANT_DYNAMIC_DATASOURCE_CONVERTS.convert(dynamicDatasource);
        eventPublisher.publishEvent(new DynamicDatasourceEvent(this, applicationContext.getId(), datasource, action.getType()));
        log.debug("event publish successful - {}", datasource);
    }
}
