package com.github.jgzl.common.api.vo;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import com.github.jgzl.common.api.dataobject.BaseDo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 角色表
 *
 * @author lihaifeng
 * @since 2020-01-12
 */
@Data
public class SysRoleVo extends BaseDo<SysRoleVo> implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	private static final String PREFIX = "ROLE_";

	/**
	 * 角色id
	 */
	private Long roleId;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 角色描述
	 */
	private String remark;

	private List<Operation> operations;

	@Override
	public String getAuthority() {
		return PREFIX + remark.toUpperCase();
	}
}
