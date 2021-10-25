package com.github.jgzl.common.api.vo;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import com.github.jgzl.common.core.sensitive.Sensitive;
import com.github.jgzl.common.core.sensitive.SensitiveTypeEnum;
import com.github.jgzl.common.api.dataobject.BaseDo;


import cn.hutool.core.collection.CollUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 单点登录用户表
 *
 * @author lihaifeng
 * @since 2020-01-12
 */
@Data
public class UserVo extends BaseDo<UserVo> implements UserDetails {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户id
	 */
	private Long userId;

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

	/**
	 * 是否过期
	 */
	private Boolean expired;

	/**
	 * 是否锁定
	 */
	private Boolean locked;

	/**
	 * 是否可用
	 */
	private Boolean enabled;

	/**
	 * 密码是否过期
	 */
	private Boolean passwordExpired;

	/**
	 * 角色列表
	 */
	private List<SysRoleVo> roles = new ArrayList<>();

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (CollUtil.isEmpty(roles)) {
			return Collections.emptyList();
		}
		final List<GrantedAuthority> authorities = new ArrayList<>(AuthorityUtils.createAuthorityList(
				roles.stream().map(SysRoleVo::getAuthority).collect(Collectors.joining())));
		roles.forEach(role -> {
			if (CollUtil.isNotEmpty(role.getOperations())) {
				authorities.addAll(AuthorityUtils.createAuthorityList(
						role.getOperations().stream().map(Operation::getAuthority).collect(Collectors.joining())));
			}
		});
		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !expired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !passwordExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
