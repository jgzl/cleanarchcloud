package com.github.jgzl.common.api.vo;
import com.github.jgzl.common.core.sensitive.Sensitive;
import com.github.jgzl.common.core.sensitive.SensitiveTypeEnum;
import com.github.jgzl.common.api.dataobject.BaseDo;
import com.github.jgzl.common.api.dataobject.SysUser;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;

/**
 * 单点登录用户表
 *
 * @author lihaifeng
 * @since 2020-01-12
 */
@Data
public class SysUserVo extends BaseDo<SysUserVo> {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private String userId;

	/**
	 * 用户姓名(账户)
	 */
	private String username;

	/**
	 * 用户昵称
	 */
	private String nickname;

	/**
	 * 用户密码
	 */
	private String password;

	/**
	 * 手机号
	 */
	@Sensitive(type = SensitiveTypeEnum.MOBILE_PHONE)
	private String mobile;

	/**
	 * 邮箱
	 */
	@Sensitive(type = SensitiveTypeEnum.EMAIL)
	private String email;

	/**
	 * 登录次数
	 */
	private Integer loginCount;

	/**
	 * 登录错误次数
	 */
	private Integer loginErrorCount;

	/**
	 * 登录时间(最新)
	 */
	private LocalDateTime loginTime;

	/**
	 * 账号状态
	 */
	private Integer loginStatus;

	/**
	 * 头像
	 */
	private String avatar;

	public SysUserVo() {
	}

	public SysUserVo(SysUser user) {
		this.setUserId(String.valueOf(user.getUserId()));
		this.setCreateUser(user.getCreateUser());
		this.setCreateDate(user.getCreateDate());
		this.setUpdateUser(user.getUpdateUser());
		this.setUpdateDate(user.getUpdateDate());
		this.setVersion(user.getVersion());
		this.setDelFlag(user.getDelFlag());
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
