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

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统社交登录账号表
 *
 * @author lihaifeng
 * @date 2018-08-16 21:30:41
 */
@Data
@ApiModel(value = "第三方账号信息")
@EqualsAndHashCode(callSuper = true)
public class PlatformSocialDetailsDO extends Model<PlatformSocialDetailsDO> {
	private static final long serialVersionUID = 1L;

	/**
	 * 主鍵
	 */
	@TableId
	@ApiModelProperty(value = "主键")
	private Integer id;

	/**
	 * 类型
	 */
	@NotBlank(message = "类型不能为空")
	@ApiModelProperty(value = "账号类型")
	private String type;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String remark;

	/**
	 * appid
	 */
	@NotBlank(message = "账号不能为空")
	@ApiModelProperty(value = "appId")
	private String appId;

	/**
	 * app_secret
	 */
	@NotBlank(message = "密钥不能为空")
	@ApiModelProperty(value = "app secret")
	private String appSecret;

	/**
	 * 回调地址
	 */
	@ApiModelProperty(value = "回调地址")
	private String redirectUrl;

	/**
	 * 创建时间
	 */
	@ApiModelProperty(value = "创建时间")
	private LocalDateTime createTime;

	/**
	 * 更新时间
	 */
	@ApiModelProperty(value = "更新时间")
	private LocalDateTime updateTime;

	/**
	 * 删除标记
	 */
	@TableLogic
	@ApiModelProperty(value = "删除标记,1:已删除,0:正常")
	private String delFlag;

	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "所属租户")
	private Integer tenantId;
}
