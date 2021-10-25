package com.github.jgzl.common.api.dataobject;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jgzl.common.core.sensitive.Sensitive;
import com.github.jgzl.common.core.sensitive.SensitiveTypeEnum;

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
@TableName("sys_user")
public class SysUser extends BaseDo<SysUser> {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	@TableId(value = "id", type = IdType.ASSIGN_ID)

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

}
