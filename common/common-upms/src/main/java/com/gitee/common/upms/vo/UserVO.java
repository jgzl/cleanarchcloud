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

package com.gitee.common.upms.vo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.common.core.sensitive.Sensitive;
import com.gitee.common.core.sensitive.SensitiveTypeEnum;
import com.gitee.common.upms.entity.PlatformRole;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author lengleng
 * @date 2017/10/29
 */
@Data
@ApiModel(value = "前端用户展示对象")
public class UserVO implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	@TableId(value = "id", type = IdType.ID_WORKER)
	private Long id;

	@ApiModelProperty(value = "用户姓名(账户)")
	private String username;

	@ApiModelProperty(value = "用户昵称")
	private String nickname;

	@ApiModelProperty(value = "用户密码")
	private String password;

	@Sensitive(type = SensitiveTypeEnum.MOBILE_PHONE)
	@ApiModelProperty(value = "手机号")
	private String mobile;

	@Sensitive(type = SensitiveTypeEnum.EMAIL)
	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "登录次数")
	private Integer loginCount;

	@ApiModelProperty(value = "登录错误次数")
	private Integer loginErrorCount;

	@ApiModelProperty(value = "登录时间(最新)")
	private LocalDateTime loginTime;

	@ApiModelProperty(value = "账号状态")
	private Integer loginStatus;

	@ApiModelProperty(value = "头像")
	private String avatar;

	/** 创建人 */
	@ApiModelProperty("创建人")
	private Long createUser ;

	/** 创建时间 */
	@ApiModelProperty("创建时间")
	private LocalDateTime createDate ;

	/** 更新人 */
	@ApiModelProperty("更新人")
	private Long updateUser ;

	/** 更新时间 */
	@ApiModelProperty("更新时间")
	private LocalDateTime updateDate ;

	/** 拥有的角色列表 */
	@ApiModelProperty(value = "拥有的角色列表")
	private List<PlatformRole> roleList;
}
