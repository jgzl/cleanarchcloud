/*
 *    Copyright [2020] [lihaifeng,xuhang]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.gitee.common.upms.dao;

import javax.validation.constraints.NotBlank;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 客户端信息
 * </p>
 *
 * @author lihaifeng
 * @since 2018-05-15
 */
@Data
@ApiModel(value = "客户端信息")
@EqualsAndHashCode(callSuper = true)
public class PlatformOauthClientDetailsDAO extends Model<PlatformOauthClientDetailsDAO> {

	private static final long serialVersionUID = 1L;
	/**
	 * 客户端ID
	 */
	@NotBlank(message = "client_id 不能为空")
	@TableId(value = "client_id", type = IdType.INPUT)
	@ApiModelProperty(value = "客户端id")
	private String clientId;
	/**
	 * 客户端密钥
	 */
	@NotBlank(message = "client_secret 不能为空")
	@ApiModelProperty(value = "客户端密钥")
	private String clientSecret;
	/**
	 * 资源ID
	 */
	@ApiModelProperty(value = "资源id列表")
	private String resourceIds;
	/**
	 * 作用域
	 */
	@NotBlank(message = "scope 不能为空")
	@ApiModelProperty(value = "作用域")
	private String scope;
	/**
	 * 授权方式（A,B,C）
	 */
	@ApiModelProperty(value = "授权方式")
	private String authorizedGrantTypes;
	/**
	 * 回调地址
	 */
	@ApiModelProperty(value = "回调地址")
	private String webServerRedirectUri;
	/**
	 * 权限
	 */
	@ApiModelProperty(value = "权限列表")
	private String authorities;
	/**
	 * 请求令牌有效时间
	 */
	@ApiModelProperty(value = "请求令牌有效时间")
	private Integer accessTokenValidity;
	/**
	 * 刷新令牌有效时间
	 */
	@ApiModelProperty(value = "刷新令牌有效时间")
	private Integer refreshTokenValidity;
	/**
	 * 扩展信息
	 */
	@ApiModelProperty(value = "扩展信息")
	private String additionalInformation;
	/**
	 * 是否自动放行
	 */
	@ApiModelProperty(value = "是否自动放行")
	private String autoapprove;
}
