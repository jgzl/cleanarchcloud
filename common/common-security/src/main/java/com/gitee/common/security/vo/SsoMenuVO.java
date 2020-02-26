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

import java.math.BigDecimal;

import com.gitee.common.data.bo.BaseDO;

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
@ApiModel(value = "SsoMenu对象", description = "菜单表")
public class SsoMenuVO extends BaseDO<SsoMenuVO> {

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
