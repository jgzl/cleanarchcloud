package com.github.jgzl.infra.upms.domain.entity.baseinfo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.github.jgzl.common.core.model.SuperEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("sys_social_user_auth")
public class SocialUserAuth extends SuperEntity<Long> {
	/**
	 * 用户表id
	 */
	private Long userId;
	/**
	 * 社会登录表id
	 */
	private Long socialUserId;
}
