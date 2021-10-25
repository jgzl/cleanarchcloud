package com.github.jgzl.common.api.vo;
import java.math.BigDecimal;
import com.github.jgzl.common.api.dataobject.BaseDo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 菜单表
 *
 * @author lihaifeng
 * @since 2020-01-12
 */
@Data
public class SysMenuVo extends BaseDo<SysMenuVo> {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单/按钮id
	 */
	private Long menuId;

	/**
	 * 上级菜单id
	 */
	private Long parentId;

	/**
	 * 菜单/按钮名称
	 */
	private String menuName;

	/**
	 * 对应路由path
	 */
	private String path;

	/**
	 * 对应路由组件component
	 */
	private String component;

	/**
	 * 权限标识
	 */
	private String perms;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 类型 0菜单 1按钮
	 */
	private String type;

	/**
	 * 排序
	 */
	private BigDecimal orderNum;
}
