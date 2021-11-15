package com.github.jgzl.infra.upms.service;
import com.github.jgzl.common.data.configuration.dynamic.event.body.EventAction;
import com.github.jgzl.common.data.mybatis.SuperService;
import com.github.jgzl.infra.upms.domain.entity.tenant.DynamicDatasource;
import com.github.jgzl.infra.upms.domain.vo.TenantDynamicDatasourceVO;
import java.util.List;
/**
 * @author Levvin
 */
public interface DynamicDatasourceService extends SuperService<DynamicDatasource> {
    /**
     * 查询所有可用的动态数据源
     *
     * @return 查询结果
     */
    List<TenantDynamicDatasourceVO> selectTenantDynamicDatasource();
    void ping(Long id);
    void saveOrUpdateDatabase(DynamicDatasource dynamicDatasource);
    void removeDatabaseById(Long id);
    void publishEvent(EventAction action, Long tenantId);
}
