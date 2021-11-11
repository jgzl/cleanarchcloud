package com.github.jgzl.infra.upms.service;

import com.github.jgzl.common.data.mybatis.SuperService;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.UserRole;
import com.github.jgzl.infra.upms.domain.vo.UserRoleResp;

/**
 * <p>
 * 业务接口
 * 角色分配
 * 账号角色绑定
 * </p>
 *
 * @author Levin
 * @since 2019-07-03
 */
public interface UserRoleService extends SuperService<UserRole> {

    /**
     * 根据劫色查询用户
     *
     * @param roleId 角色id
     * @return 查询结果
     */
    UserRoleResp findUserByRoleId(Long roleId);

}
