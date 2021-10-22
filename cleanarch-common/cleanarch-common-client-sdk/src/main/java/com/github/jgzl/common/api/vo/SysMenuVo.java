package com.github.jgzl.common.api.vo;

import java.math.BigDecimal;

import com.github.jgzl.common.api.dataobject.BaseDo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author lihaifeng
 * @since 2020-01-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SysMenu对象", description = "菜单表")
public class SysMenuVo extends BaseDo<SysMenuVo> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "菜单/按钮id")
	private Long menuId;

	@ApiModelProperty(value = "上级菜单id")
	private Long parentId;

	@ApiModelProperty(value = "菜单/按钮名称")
	private String menuName;

	@ApiModelProperty(value = "对应路由path")
	private String path;

	@ApiModelProperty(value = "对应路由组件component")
	private String component;

	@ApiModelProperty(value = "权限标识")
	private String perms;

	@ApiModelProperty(value = "图标")
	private String icon;

	@ApiModelProperty(value = "类型 0菜单 1按钮")
	private String type;

	@ApiModelProperty(value = "排序")
	private BigDecimal orderNum;
}
