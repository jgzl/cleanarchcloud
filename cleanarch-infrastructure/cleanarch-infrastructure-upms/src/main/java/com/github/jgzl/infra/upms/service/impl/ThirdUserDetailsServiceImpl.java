package com.github.jgzl.infra.upms.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.jgzl.common.api.dataobject.UserInfoDetails;
import com.github.jgzl.common.core.exception.CheckedException;
import com.github.jgzl.common.core.util.StringUtils;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.common.data.tenant.TenantContextHolder;
import com.github.jgzl.common.security.exception.BusinessException;
import com.github.jgzl.common.security.util.SecurityUtils;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.Role;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.User;
import com.github.jgzl.infra.upms.domain.entity.tenant.Tenant;
import com.github.jgzl.infra.upms.repository.ResourceMapper;
import com.github.jgzl.infra.upms.repository.RoleMapper;
import com.github.jgzl.infra.upms.service.TenantService;
import com.github.jgzl.infra.upms.service.ThirdUserDetailsService;
import com.github.jgzl.infra.upms.service.UserService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * 用户信息获取
 *
 * @author lihaifeng
 * @date 2020/7/24 17:06
 */
@Service
public class ThirdUserDetailsServiceImpl implements ThirdUserDetailsService {

	@Autowired
    private UserService userService;
	@Autowired
	private TenantService tenantService;
	@Autowired
	private RoleMapper roleMapper;
	@Autowired
	private ResourceMapper resourceMapper;

	@Transactional
	@Override
	public UserInfoDetails loadUserByUniqueKey(final Authentication authentication) {
		AuthUser authUser = (AuthUser)authentication.getPrincipal();
		String uuid = authUser.getUuid();
		String source = authUser.getSource();
		UserInfoDetails userInfoDetails = SecurityUtils.getAuthInfo();
		List<User> sysUsers = userService.findUserBySocialUserUuidAndSource(uuid,source);
		if (userInfoDetails!=null) {
			if (CollUtil.isNotEmpty(sysUsers)) {
				throw new BusinessException("当前账号已经绑定第三方平台["+source+"]的账户,请解绑后再用第三方平台账户绑定当前登录账号");
			}else {
				userService.bindSocialUser(authUser,userInfoDetails.getUserId());
			}
			return userInfoDetails;
		}else {
			String tenantCode = TenantContextHolder.getTenantCode();
			final Tenant tenant = Optional.ofNullable(tenantService.getOne(Wraps.<Tenant>lbQ().eq(Tenant::getCode, tenantCode)))
					.orElseThrow(() -> CheckedException.notFound("{1}租户不存在", tenantCode));
			User user;
			if (CollUtil.isEmpty(sysUsers)) {
				user = new User();
				user.setUsername(source+"_"+uuid);
				user.setPassword(RandomUtil.randomStringUpper(32));
				user.setNickName(authUser.getNickname());
				user.setAvatar(authUser.getAvatar());
				user.setEmail(authUser.getEmail());
				user.setTenantId(tenant.getId());
				userService.save(user);
				// 第三方账户创建并绑定用户
				userService.bindSocialUser(authUser,user.getId());
			}else {
				user = sysUsers.get(0);
			}
			if (!Objects.equals(tenant.getId(),user.getTenantId())) {
				throw CheckedException.notFound("账户不存在");
			}
			userInfoDetails = new UserInfoDetails();
			userInfoDetails.setTenantCode(tenantCode);
			userInfoDetails.setTenantId(user.getTenantId());
			userInfoDetails.setUserId(user.getId());
			userInfoDetails.setUsername(user.getUsername());
			userInfoDetails.setRealName(user.getNickName());
			userInfoDetails.setNickName(user.getNickName());
			userInfoDetails.setMobile(user.getMobile());
			userInfoDetails.setEmail(user.getEmail());
			userInfoDetails.setDescription(user.getDescription());
			userInfoDetails.setSex(Objects.isNull(user.getSex()) ? null : user.getSex().getValue());
			userInfoDetails.setEnabled(user.getStatus());
			userInfoDetails.setAvatar(user.getAvatar());
			userInfoDetails.setPassword(user.getPassword());
			setAuthorize(userInfoDetails);
			return userInfoDetails;
		}
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
