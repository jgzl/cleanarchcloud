package com.github.jgzl.infra.upms.service.impl;

import com.github.jgzl.common.security.dataobject.UserInfoDetails;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.core.exception.CheckedException;
import com.github.jgzl.common.core.util.StringUtils;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.common.data.tenant.TenantContextHolder;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.Role;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.User;
import com.github.jgzl.infra.upms.domain.entity.tenant.Tenant;
import com.github.jgzl.infra.upms.repository.ResourceMapper;
import com.github.jgzl.infra.upms.repository.RoleMapper;
import com.github.jgzl.infra.upms.service.TenantService;
import com.github.jgzl.infra.upms.service.UserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 *
 * @author lihaifeng
 * 2019/5/14 13:56
 */
@Service
public class MobileUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;
	@Autowired
	private TenantService tenantService;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private ResourceMapper resourceMapper;

	@Cacheable(value = CacheConstants.USER_DETAILS,key = "#username",unless = "#result==null")
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.findUserByMobile(username);
		if (user==null) {
			throw CheckedException.notFound("账户不存在");
		}
		UserInfoDetails info;
		String tenantCode = TenantContextHolder.getTenantCode();
		final Tenant tenant = Optional.ofNullable(tenantService.getOne(Wraps.<Tenant>lbQ().eq(Tenant::getCode, tenantCode)))
				.orElseThrow(() -> CheckedException.notFound("{}租户不存在", tenantCode));
		if (!Objects.equals(tenant.getId(),user.getTenantId())) {
			throw CheckedException.notFound("账户不存在");
		}
		info = new UserInfoDetails();
		info.setTenantCode(tenantCode);
		info.setTenantId(user.getTenantId());
		info.setUserId(user.getId());
		info.setUsername(user.getUsername());
		info.setRealName(user.getUsername());
		info.setNickName(user.getNickName());
		info.setMobile(user.getMobile());
		info.setEmail(user.getEmail());
		info.setDescription(user.getDescription());
		info.setSex(Objects.isNull(user.getSex()) ? null : user.getSex().getValue());
		info.setEnabled(user.getStatus());
		info.setAvatar(user.getAvatar());
		info.setPassword(user.getPassword());
		setAuthorize(info);
		return info;
	}

	/**
	 * 设置授权信息
	 *
	 * @param user user
	 */
	private void setAuthorize(UserInfoDetails user) {
		final List<String> roles = Optional.ofNullable(this.roleMapper.findRoleByUserId(user.getUserId())).orElseGet(Lists::newArrayList)
				.stream().map(Role::getCode).collect(toList());
		final List<String> permissions = Optional.ofNullable(this.resourceMapper.queryPermissionByUserId(user.getUserId())).orElseGet(Lists::newArrayList);
		// 验证角色和登录系统
		Set<String> authorize = Sets.newHashSet();
		authorize.addAll(roles);
		authorize.addAll(permissions);
		user.setRoles(roles);
		user.setPermissions(permissions);
		user.setAuthorities(authorize.stream().filter(StringUtils::isNotBlank).map(SimpleGrantedAuthority::new).collect(Collectors.toSet()));
	}
}
