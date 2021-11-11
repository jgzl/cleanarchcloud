package com.github.jgzl.infra.upms.repository;

import com.github.jgzl.common.data.configuration.dynamic.ann.DynamicDS;
import com.github.jgzl.common.data.mybatis.SuperMapper;
import com.github.jgzl.infra.upms.domain.entity.common.AreaEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Levin
 */
@DynamicDS
@Mapper
public interface AreaMapper extends SuperMapper<AreaEntity> {

    /**
     * 根据 parentId 查询数据集
     *
     * @param parentId parentId
     * @return 查询结果
     */
    List<AreaEntity> listArea(@Param("parentId") Integer parentId);
}
