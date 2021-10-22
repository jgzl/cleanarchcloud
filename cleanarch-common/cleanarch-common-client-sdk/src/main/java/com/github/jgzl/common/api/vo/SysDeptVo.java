package com.github.jgzl.common.api.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.jgzl.common.api.dataobject.BaseDo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 部门表
 * </p>
 *
 * @author lihaifeng
 * @since 2020-01-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SysDept对象", description = "部门表")
public class SysDeptVo extends BaseDo<SysDeptVo> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "部门id")
	@TableId(value = "dept_id", type = IdType.ASSIGN_ID)
	private Long deptId;

	@ApiModelProperty(value = "上级部门id")
	private Long parentId;

	@ApiModelProperty(value = "部门名称")
	private String deptName;

	@ApiModelProperty(value = "排序")
	private Double orderNum;
}