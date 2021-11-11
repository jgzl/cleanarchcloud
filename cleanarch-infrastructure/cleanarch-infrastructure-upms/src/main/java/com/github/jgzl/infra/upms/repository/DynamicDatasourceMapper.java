package com.github.jgzl.infra.upms.repository;

import com.github.jgzl.common.data.mybatis.SuperMapper;
import com.github.jgzl.infra.upms.domain.entity.tenant.DynamicDatasource;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author levin
 */
@Mapper
public interface DynamicDatasourceMapper extends SuperMapper<DynamicDatasource> {


}
