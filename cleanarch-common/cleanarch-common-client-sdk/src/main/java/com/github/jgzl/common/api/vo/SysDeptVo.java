package com.github.jgzl.common.api.vo;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.jgzl.common.api.dataobject.BaseDo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 部门表
 *
 * @author lihaifeng
 * @since 2020-01-12
 */
@Data
public class SysDeptVo extends BaseDo<SysDeptVo> {

	private static final long serialVersionUID = 1L;

	/**
	 * 部门id
	 */
	@TableId(value = "dept_id", type = IdType.ASSIGN_ID)
	private Long deptId;

	/**
	 * 上级部门id
	 */
	private Long parentId;

	/**
	 * 部门名称
	 */
	private String deptName;

	/**
	 * 排序
	 */
	private String orderNum;
}
