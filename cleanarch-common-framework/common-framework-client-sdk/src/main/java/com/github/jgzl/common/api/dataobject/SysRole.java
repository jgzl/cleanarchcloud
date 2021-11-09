package com.github.jgzl.common.api.dataobject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import lombok.Data;

/**
 * 角色表
 *
 * @author lihaifeng
 * @since 2020-01-12
 */
@Data
@TableName("sys_role")
public class SysRole extends BaseDo<SysRole> {

	private static final long serialVersionUID = 1L;

	/**
	 * 角色id
	 */
	@TableId(value = "role_id", type = IdType.ASSIGN_ID)
	private Long roleId;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 角色描述
	 */
	private String remark;
}
