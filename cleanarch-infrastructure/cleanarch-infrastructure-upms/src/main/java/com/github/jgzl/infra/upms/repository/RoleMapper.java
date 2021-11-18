package com.github.jgzl.infra.upms.repository;

import com.github.jgzl.common.data.configuration.dynamic.ann.DynamicDS;
import com.github.jgzl.common.data.mybatis.SuperMapper;
import com.github.jgzl.common.data.mybatis.auth.DataScope;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lihaifeng
 */
@DynamicDS
@Mapper
public interface RoleMapper extends SuperMapper<Role> {


    /**
     * 根据用户ID查询角色
     *
     * @param userId 用户ID
     * @return 查询结果
     */
    List<Role> findRoleByUserId(Long userId);

    /**
     * 根据范围查询角色
     *
     * @param scope scope
     * @return 查询结果
     */
    List<Role> list(DataScope scope);
}
