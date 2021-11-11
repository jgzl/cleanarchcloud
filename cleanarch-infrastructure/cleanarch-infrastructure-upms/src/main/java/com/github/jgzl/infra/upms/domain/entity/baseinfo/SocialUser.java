package com.github.jgzl.infra.upms.domain.entity.baseinfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jgzl.common.core.model.SuperEntity;
import lombok.Builder;
import lombok.Data;

/**
 * 社会登录用户表(第三方平台用户表)
 */
@Data
@Builder
@TableName("sys_social_user")
public class SocialUser extends SuperEntity<Long> {
	/**
	 * 第三方系统唯一id
	 */
	private String uuid;
	/**
	 * 第三方用户来源
	 */
	private String source;
	/**
	 * 用户的授权令牌
	 */
	private String accessToken;
	/**
	 * 第三方用户的授权令牌的有效期
	 */
	private Integer expireIn;
	/**
	 * 刷新令牌
	 */
	private String refreshToken;
	/**
	 * 第三方用户的刷新令牌的有效期
	 */
	private int refreshTokenExpireIn;
	/**
	 * 第三方用户的 open id
	 */
	private String openId;
	/**
	 * 第三方用户的 ID
	 */
	private String uid;
	/**
	 * 个别平台的授权信息
	 */
	private String accessCode;
	/**
	 * 第三方用户的 union id
	 */
	private String unionId;
	/**
	 * 第三方用户授予的权限
	 */
	private String scope;
	/**
	 * 个别平台的授权信息
	 */
	private String tokenType;
	/**
	 * id token
	 */
	private String idToken;
	/**
	 * 小米平台用户的附带属性
	 */
	private String macAlgorithm;
	/**
	 * 小米平台用户的附带属性
	 */
	private String macKey;
	/**
	 * 用户的授权code
	 */
	private String code;
	/**
	 * Twitter平台用户的附带属性
	 */
	private String oauthToken;
	/**
	 * Twitter平台用户的附带属性
	 */
	private String oauthTokenSecret;
	/**
	 * 用户姓名(账户)
	 */
	private String username;
	/**
	 * 用户昵称
	 */
	private String nickname;
	/**
	 * 邮箱
	 */
	private String email;
	/**
	 * 头像
	 */
	private String avatar;
}
