package com.github.jgzl.common.api.dataobject;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import lombok.Data;

/**
 * 菜单表
 *
 * @author lihaifeng
 * @since 2020-01-12
 */
@Data
@TableName("sys_menu")
public class SysMenu extends BaseDo<SysMenu> {

	private static final long serialVersionUID = 1L;

	/**
	 * 菜单/按钮id
	 */
	@TableId(value = "menu_id", type = IdType.ASSIGN_ID)
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
