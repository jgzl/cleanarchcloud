package com.gitee.common.security.vo;

import java.time.LocalDateTime;

import com.gitee.common.core.sensitive.Sensitive;
import com.gitee.common.core.sensitive.SensitiveTypeEnum;
import com.gitee.common.data.bo.BaseDO;
import com.gitee.common.security.dataobject.SysUserDO;

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
@ApiModel(value = "SysUser对象", description = "单点登录用户表")
public class SysUserVO extends BaseDO<SysUserVO> {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户id")
	private String userId;

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

	public SysUserVO() {
	}

	public SysUserVO(SysUserDO user) {
		this.setUserId(String.valueOf(user.getUserId()));
		this.setCreateUser(user.getCreateUser());
		this.setCreateDate(user.getCreateDate());
		this.setUpdateUser(user.getUpdateUser());
		this.setUpdateDate(user.getUpdateDate());
		this.setVersion(user.getVersion());
		this.setDeleted(user.getDeleted());
		this.setUsername(user.getUsername());
		this.setNickname(user.getNickname());
		//不给前端传递密码 this.setPassword(user.getPassword());
		this.setMobile(user.getMobile());
		this.setEmail(user.getEmail());
		this.setLoginCount(user.getLoginCount());
		this.setLoginErrorCount(user.getLoginErrorCount());
		this.setLoginTime(user.getLoginTime());
		this.setLoginStatus(user.getLoginStatus());
		this.setAvatar(user.getAvatar());
	}
}
