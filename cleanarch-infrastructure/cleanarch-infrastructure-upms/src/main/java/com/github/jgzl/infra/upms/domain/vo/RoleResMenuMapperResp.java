package com.github.jgzl.infra.upms.domain.vo;
import lombok.Data;
/**
 * @author lihaifeng
 */
@Data
public class RoleResMenuMapperResp {
	/**
	 * 资源ID
	 */
	private Long id;
	/**
	 * 角色ID
	 */
	private Long roleId;
	/**
	 * 资源类型（1=菜单;2=按钮）
	 */
	private Integer type;
}
