package com.github.jgzl.infra.upms.repository;
import com.github.jgzl.common.data.configuration.dynamic.ann.DynamicDS;
import com.github.jgzl.common.data.mybatis.SuperMapper;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
/**
 * <p>
 * Mapper 接口
 * 角色分配
 * 账号角色绑定
 * </p>
 *
 * @author Levin
 * @since 2019-07-03
 */
@DynamicDS
@Mapper
public interface UserRoleMapper extends SuperMapper<UserRole> {

}
