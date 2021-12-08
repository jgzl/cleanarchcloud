package com.github.jgzl.infra.upms.mapper;

import com.github.jgzl.infra.upms.api.entity.SysMenu;
import com.github.jgzl.common.data.datascope.ExtendBaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 菜单权限表 Mapper 接口
 * </p>
 *
 * @author lihaifeng
 * @since 2017-10-29
 */
@Mapper
public interface SysMenuMapper extends ExtendBaseMapper<SysMenu> {

	/**
	 * 通过角色编号查询菜单
	 * @param roleId 角色ID
	 * @return
	 */
	List<SysMenu> listMenusByRoleId(Integer roleId);

	/**
	 * 通过角色ID查询权限
	 * @param roleIds Ids
	 * @return
	 */
	List<String> listPermissionsByRoleIds(String roleIds);

}
