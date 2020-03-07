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

package com.gitee.common.upms.dao;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.common.data.bo.BaseDO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户操作日志表
 * </p>
 *
 * @author lihaifeng
 * @since 2020-01-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "PlatformLog对象", description = "用户操作日志表")
@TableName("platform_log")
public class PlatformLogDO extends BaseDO<PlatformLogDO> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "日志id")
	@TableId(value = "log_id", type = IdType.ASSIGN_ID)
	private Long logId;

	@ApiModelProperty(value = "操作用户")
	private String username;

	@ApiModelProperty(value = "操作内容")
	private String operation;

	@ApiModelProperty(value = "耗时")
	private BigDecimal time;

	@ApiModelProperty(value = "操作方法")
	private String method;

	@ApiModelProperty(value = "方法参数")
	private String params;

	@ApiModelProperty(value = "操作者ip")
	private String ip;

	@ApiModelProperty(value = "操作地点")
	private String location;
}
