package com.github.jgzl.infra.upms.service;

import com.github.jgzl.common.data.mybatis.SuperService;
import com.github.jgzl.infra.upms.domain.dto.RoleResSaveDTO;
import com.github.jgzl.infra.upms.domain.dto.UserRoleSaveDTO;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.RoleRes;
import com.github.jgzl.infra.upms.domain.vo.RoleResVO;

/**
 * <p>
 * 业务接口
 * 角色的资源
 * </p>
 *
 * @author Levin
 * @since 2019-07-03
 */
public interface RoleResService extends SuperService<RoleRes> {

    /**
     * 给用户分配角色
     *
     * @param userRole userRole
     * @return 保存结果
     */
    boolean saveUserRole(UserRoleSaveDTO userRole);

    /**
     * 给角色重新分配 权限（资源/菜单）
     *
     * @param roleResSaveDTO
     * @return
     */
    void saveRoleAuthority(RoleResSaveDTO roleResSaveDTO);

    /**
     * 根据角色id查询资源
     *
     * @param id id
     * @return 查询结果
     */
    RoleResVO findAuthorityIdByRoleId(Long id);
}
