package com.github.jgzl.common.api.vo;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.github.jgzl.common.api.dataobject.BaseDo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author lihaifeng
 * @since 2020-01-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "Role对象", description = "角色表")
public class SysRoleVo extends BaseDo<SysRoleVo> implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	private static final String PREFIX = "ROLE_";

	@ApiModelProperty(value = "角色id")
	private Long roleId;

	@ApiModelProperty(value = "角色名称")
	private String roleName;

	@ApiModelProperty(value = "角色描述")
	private String remark;

	private List<Operation> operations;

	@Override
	public String getAuthority() {
		return PREFIX + remark.toUpperCase();
	}
}
