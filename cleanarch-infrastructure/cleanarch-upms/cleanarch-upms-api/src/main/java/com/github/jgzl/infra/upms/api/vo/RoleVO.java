package com.github.jgzl.infra.upms.api.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author lihaifeng
 * @date 2020/2/10
 */
@Data
@ApiModel(value = "前端角色展示对象")
public class RoleVO {

	/**
	 * 角色id
	 */
	private Integer roleId;

	/**
	 * 菜单列表
	 */
	private String menuIds;

}
