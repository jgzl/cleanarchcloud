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
 * 平台系统用户关联第三方用户表
 * </p>
 *
 * @author lihaifeng
 * @since 2020-01-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "PlatformUserConnection对象", description = "平台系统用户关联第三方用户表")
@TableName("platform_user_connection")
public class PlatformUserConnectionDO extends BaseDO<PlatformUserConnectionDO> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "平台系统用户关联第三方用户id")
	@TableId(value = "user_connection_id", type = IdType.ID_WORKER)
	private Long userConnectionId;

	@ApiModelProperty(value = "平台系统用户名")
	private String userName;

	@ApiModelProperty(value = "第三方平台名称")
	private String providerName;

	@ApiModelProperty(value = "第三方平台账户id")
	private String providerUserId;

	@ApiModelProperty(value = "第三方平台用户名")
	private String providerUserName;

	@ApiModelProperty(value = "第三方平台昵称")
	private String nickName;

	@ApiModelProperty(value = "第三方平台头像")
	private String imageUrl;

	@ApiModelProperty(value = "地址")
	private String location;

	@ApiModelProperty(value = "备注")
	private String remark;
}
