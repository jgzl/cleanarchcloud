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

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基础对象
 * @author lihaifeng
 */
@Data
@ApiModel("基础对象")
public class BaseVo<M> implements Serializable, Cloneable {

		/**
		 * 创建人
		 */
		private Long createUser;

		/**
		 * 创建时间
		 */
		private LocalDateTime createDate;

		/**
		 * 更新人
		 */
		private Long updateUser;

		/**
		 * 更新时间
		 */
		private LocalDateTime updateDate;

		/**
		 * 乐观锁
		 */
		private Integer version;

		/**
		 * 逻辑删除
		 */
		private Integer deleted;
}
