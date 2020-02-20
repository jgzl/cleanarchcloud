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

package com.gitee.common.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.common.data.bo.BaseDO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 日志格式的模板
 * 因为用户名需要做唯一设置,所以可以直接通过用户名查询相关的操作日志
 * 当然了还可以添加用户id来查询日志信息
 * @author xuhang
 */
@Setter
@Getter
@ApiModel(description = "日志格式信息封装类")
@TableName("operate_log")
public class OperateLog extends BaseDO<OperateLog> implements Serializable {

	@ApiModelProperty("主键id")
	@TableId(type = IdType.ID_WORKER)
	private String id;

	@ApiModelProperty("用户Id")
	private Long userId;

	@ApiModelProperty("用户名称")
	private String username;

	@ApiModelProperty("用户姓名")
	private String fullName;

	@ApiModelProperty("所属部门(机构名称)")
	private String orgName;

	@ApiModelProperty("所属部门(机构码,解决名称变更造成的问题)")
	private String orgCode;

	@ApiModelProperty("操作时间")
	private LocalDateTime operateTime;

	@ApiModelProperty("操作模块")
	private String operateModule;

	@ApiModelProperty("操作内容")
	private String operateContent;

	@ApiModelProperty("服务名称(微服务模块名)")
	private String serviceName;

	public OperateLog() {
		this.operateTime = LocalDateTime.now();
	}
}
