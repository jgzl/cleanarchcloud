package com.github.jgzl.infra.upms.dataobject;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.github.jgzl.common.api.dataobject.BaseDo;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SysSocialUserAuth extends BaseDo<SysSocialUserAuth> {
	/**
	 * 主键
	 */
	@TableId(type = IdType.ASSIGN_ID)
	private Long id;
	/**
	 * 用户表id
	 */
	private Long userId;
	/**
	 * 社会登录表id
	 */
	private Long socialUserId;
}
