/*
 * Copyright [2020] [lihaifeng,xuhang]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.github.jgzl.common.security.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.jgzl.common.data.bo.BaseDO;

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
public class SysDeptVO extends BaseDO<SysDeptVO> {

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
