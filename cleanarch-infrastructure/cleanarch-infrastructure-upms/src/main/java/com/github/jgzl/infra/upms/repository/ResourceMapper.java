package com.github.jgzl.infra.upms.repository;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.github.jgzl.common.data.configuration.dynamic.ann.DynamicDS;
import com.github.jgzl.common.data.mybatis.SuperMapper;
import com.github.jgzl.infra.upms.domain.dto.ResourceQueryDTO;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.Resource;
import com.github.jgzl.infra.upms.domain.vo.VueRouter;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 资源
 * </p>
 *
 * @author Levin
 * @since 2019-07-03
 */
@DynamicDS
@Repository
public interface ResourceMapper extends SuperMapper<Resource> {
    /**
     * 查询 拥有的资源
     *
     * @param resource 资源对象
     * @return 查询结果
     */
    List<VueRouter> findVisibleResource(@Param("resource") ResourceQueryDTO resource);

    /**
     * 查询指定用户资源权限
     *
     * @param userId 用户ID
     * @return 查询结果
     */
    @InterceptorIgnore(tenantLine = "true")
    List<String> queryPermissionByUserId(Long userId);

    /**
     * 根据ID的获取子集
     *
     * @param id id
     * @return 查询结果
     */
    List<Resource> findChildrenById(Long id);

    /**
     * 根据父级ID的获取全部父级
     *
     * @param parentId parentId
     * @return 查询结果
     */
    @InterceptorIgnore(tenantLine = "true")
    String getTreePathByParentId(Long parentId);
}
