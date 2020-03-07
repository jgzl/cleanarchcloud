package com.gitee.common.security.dao;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.common.core.sensitive.Sensitive;
import com.gitee.common.core.sensitive.SensitiveTypeEnum;
import com.gitee.common.data.bo.BaseDO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 单点登录用户表
 * </p>
 *
 * @author lihaifeng
 * @since 2020-01-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SsoUser对象", description = "单点登录用户表 ")
@TableName("sso_user")
public class SsoUserDAO extends BaseDO<SsoUserDAO> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	@TableId(value = "id", type = IdType.ASSIGN_ID)
	private Long userId;

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
}
