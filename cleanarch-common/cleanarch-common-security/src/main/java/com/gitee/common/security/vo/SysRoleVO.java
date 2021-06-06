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

package com.gitee.common.security.vo;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.gitee.common.data.bo.BaseDO;

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
public class SysRoleVO extends BaseDO<SysRoleVO> implements GrantedAuthority {

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
