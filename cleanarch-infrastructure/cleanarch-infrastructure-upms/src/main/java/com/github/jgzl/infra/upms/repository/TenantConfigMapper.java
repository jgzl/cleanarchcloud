package com.github.jgzl.infra.upms.repository;

import com.github.jgzl.common.data.mybatis.SuperMapper;
import com.github.jgzl.infra.upms.domain.entity.tenant.TenantConfig;
import com.github.jgzl.infra.upms.domain.vo.TenantDynamicDatasourceVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lihaifeng
 */
@Mapper
public interface TenantConfigMapper extends SuperMapper<TenantConfig> {

    /**
     * 查询所有可用的动态数据源
     *
     * @param datasourceId datasourceId
     * @return 查询结果
     */
    List<TenantDynamicDatasourceVO> selectTenantDynamicDatasource(@Param("datasourceId") Long datasourceId);

    /**
     * 获取租户动态数据源
     *
     * @param tenantId tenantId
     * @return 查询结果
     */
    TenantDynamicDatasourceVO getTenantDynamicDatasourceByTenantId(Long tenantId);

}
