package com.github.jgzl.infra.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jgzl.infra.upms.api.entity.SysRole;
import com.github.jgzl.infra.upms.api.vo.RoleVO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author lihaifeng
 * @since 2017-10-29
 */
public interface SysRoleService extends IService<SysRole> {

	/**
	 * 通过用户ID，查询角色信息
	 * @param userId
	 * @return
	 */
	List<SysRole> findRolesByUserId(Integer userId);

	/**
	 * 根据角色ID 查询角色列表
	 * @param roleIdList 角色ID列表
	 * @param key 缓存key
	 * @return
	 */
	List<SysRole> findRolesByRoleIds(List<Integer> roleIdList, String key);

	/**
	 * 通过角色ID，删除角色
	 * @param id
	 * @return
	 */
	Boolean removeRoleById(Integer id);

	/**
	 * 根据角色菜单列表
	 * @param roleVo 角色&菜单列表
	 * @return
	 */
	Boolean updateRoleMenus(RoleVO roleVo);

}
