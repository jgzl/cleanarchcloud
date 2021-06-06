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

package com.github.jgzl.common.data.bo;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lihaifeng
 */
@Data
@ApiModel("VIEW基础对象")
public class BaseVO<M> implements Serializable, Cloneable {

	@ApiModelProperty("创建人")
	private Long createUser;

	@ApiModelProperty("创建时间")
	private LocalDateTime createDate;

	@ApiModelProperty("更新人")
	private Long updateUser;

	@ApiModelProperty("更新时间")
	private LocalDateTime updateDate;

	@Version
	@ApiModelProperty("乐观锁")
	private Integer version;

	@TableLogic
	@ApiModelProperty("逻辑删除")
	private Integer deleted;
}
