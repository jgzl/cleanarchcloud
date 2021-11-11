package com.github.jgzl.infra.upms.repository;

import com.github.jgzl.common.data.mybatis.SuperMapper;
import com.github.jgzl.infra.upms.domain.entity.tenant.Tenant;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Levin
 */
@Mapper
public interface TenantMapper extends SuperMapper<Tenant> {

    /**
     * 测试数据源获取数据
     *
     * @return 查询结果
     */
    @Select("select * from t_tenant")
    List<Tenant> queryDbTestTenant();
}
