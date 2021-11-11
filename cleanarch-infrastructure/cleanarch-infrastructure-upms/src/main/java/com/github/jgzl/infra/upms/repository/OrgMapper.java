package com.github.jgzl.infra.upms.repository;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.github.jgzl.common.data.configuration.dynamic.ann.DynamicDS;
import com.github.jgzl.common.data.mybatis.SuperMapper;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.Org;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Levin
 */
@DynamicDS
@Mapper
public interface OrgMapper extends SuperMapper<Org> {


    /**
     * 根据父级ID的获取全部父级
     *
     * @param parentId parentId
     * @return 查询结果
     */
    @InterceptorIgnore(tenantLine = "true")
    String getTreePathByParentId(Long parentId);

    /**
     * 根据ID的获取子集
     *
     * @param id id
     * @return 查询结果
     */
    List<Org> findChildrenById(Long id);

}
