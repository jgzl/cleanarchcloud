package com.github.jgzl.infra.upms.service;

import cn.hutool.core.lang.tree.Tree;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jgzl.infra.upms.api.entity.SysMenu;
import com.github.jgzl.common.core.util.R;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单权限表 服务类
 * </p>
 *
 * @author lihaifeng
 * @since 2017-10-29
 */
public interface SysMenuService extends IService<SysMenu> {

	/**
	 * 通过角色编号查询URL 权限
	 * @param roleId 角色ID
	 * @return 菜单列表
	 */
	List<SysMenu> findMenuByRoleId(Integer roleId);

	/**
	 * 级联删除菜单
	 * @param id 菜单ID
	 * @return 成功、失败
	 */
	R removeMenuById(Integer id);

	/**
	 * 更新菜单信息
	 * @param sysMenu 菜单信息
	 * @return 成功、失败
	 */
	Boolean updateMenuById(SysMenu sysMenu);

	/**
	 * 构建树
	 * @param lazy 是否是懒加载
	 * @param parentId 父节点ID
	 * @return
	 */
	List<Tree<Integer>> treeMenu(boolean lazy, Integer parentId);

	/**
	 * 查询菜单
	 * @param voSet
	 * @param parentId
	 * @return
	 */
	List<Tree<Integer>> filterMenu(Set<SysMenu> voSet, String type, Integer parentId);

}
