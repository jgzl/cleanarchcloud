package com.github.jgzl.infra.upms.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.lang.tree.TreeUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.core.constant.CommonConstants;
import com.github.jgzl.common.core.constant.enums.MenuTypeEnum;
import com.github.jgzl.common.core.util.R;
import com.github.jgzl.infra.upms.api.entity.SysMenu;
import com.github.jgzl.infra.upms.api.entity.SysRoleMenu;
import com.github.jgzl.infra.upms.mapper.SysMenuMapper;
import com.github.jgzl.infra.upms.mapper.SysRoleMenuMapper;
import com.github.jgzl.infra.upms.service.SysMenuService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单权限表 服务实现类
 * </p>
 *
 * @author lihaifeng
 * @since 2017-10-29
 */
@Service
@AllArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

	private final SysRoleMenuMapper sysRoleMenuMapper;

	@Override
	@Cacheable(value = CacheConstants.MENU_DETAILS, key = "#roleId", unless = "#result.isEmpty()")
	public List<SysMenu> findMenuByRoleId(Integer roleId) {
		return baseMapper.listMenusByRoleId(roleId);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
	public R removeMenuById(Integer id) {
		// 查询父节点为当前节点的节点
		List<SysMenu> menuList = this.list(Wrappers.<SysMenu>query().lambda().eq(SysMenu::getParentId, id));
		if (CollUtil.isNotEmpty(menuList)) {
			return R.failed("菜单含有下级不能删除");
		}

		sysRoleMenuMapper.delete(Wrappers.<SysRoleMenu>query().lambda().eq(SysRoleMenu::getMenuId, id));
		// 删除当前菜单及其子菜单
		return R.ok(this.removeById(id));
	}

	@Override
	@CacheEvict(value = CacheConstants.MENU_DETAILS, allEntries = true)
	public Boolean updateMenuById(SysMenu sysMenu) {
		return this.updateById(sysMenu);
	}

	/**
	 * 构建树查询 1. 不是懒加载情况，查询全部 2. 是懒加载，根据parentId 查询 2.1 父节点为空，则查询ID -1
	 *
	 * @param lazy     是否是懒加载
	 * @param parentId 父节点ID
	 * @return
	 */
	@Override
	public List<Tree<Integer>> treeMenu(boolean lazy, Integer parentId) {
		if (!lazy) {
			List<TreeNode<Integer>> collect = baseMapper
					.selectList(Wrappers.<SysMenu>lambdaQuery().orderByAsc(SysMenu::getSort)).stream()
					.map(getNodeFunction()).collect(Collectors.toList());

			return TreeUtil.build(collect, CommonConstants.MENU_TREE_ROOT_ID);
		}

		Integer parent = parentId == null ? CommonConstants.MENU_TREE_ROOT_ID : parentId;

		List<TreeNode<Integer>> collect = baseMapper
				.selectList(
						Wrappers.<SysMenu>lambdaQuery().eq(SysMenu::getParentId, parent).orderByAsc(SysMenu::getSort))
				.stream().map(getNodeFunction()).collect(Collectors.toList());

		return TreeUtil.build(collect, parent);
	}

	/**
	 * 查询菜单
	 *
	 * @param all      全部菜单
	 * @param type     类型
	 * @param parentId 父节点ID
	 * @return
	 */
	@Override
	public List<Tree<Integer>> filterMenu(Set<SysMenu> all, String type, Integer parentId) {
		List<TreeNode<Integer>> collect = all.stream().filter(menuTypePredicate(type)).map(getNodeFunction())
				.collect(Collectors.toList());
		Integer parent = parentId == null ? CommonConstants.MENU_TREE_ROOT_ID : parentId;
		return TreeUtil.build(collect, parent);
	}

	@NotNull
	private Function<SysMenu, TreeNode<Integer>> getNodeFunction() {
		return menu -> {
			TreeNode<Integer> node = new TreeNode<>();
			node.setId(menu.getMenuId());
			node.setName(menu.getName());
			node.setParentId(menu.getParentId());
			node.setWeight(menu.getSort());
			// 扩展属性
			Map<String, Object> extra = new HashMap<>();
			extra.put("icon", menu.getIcon());
			extra.put("path", menu.getPath());
			extra.put("type", menu.getType());
			extra.put("permission", menu.getPermission());
			extra.put("label", menu.getName());
			extra.put("sort", menu.getSort());
			extra.put("keepAlive", menu.getKeepAlive());
			node.setExtra(extra);
			return node;
		};
	}

	/**
	 * menu 类型断言
	 *
	 * @param type 类型
	 * @return Predicate
	 */
	private Predicate<SysMenu> menuTypePredicate(String type) {
		return vo -> {
			if (MenuTypeEnum.TOP_MENU.getDescription().equals(type)) {
				return MenuTypeEnum.TOP_MENU.getType().equals(vo.getType());
			}
			// 其他查询 左侧 + 顶部
			return !MenuTypeEnum.BUTTON.getType().equals(vo.getType());
		};
	}

}
