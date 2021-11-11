package com.github.jgzl.infra.upms.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import com.github.jgzl.common.api.dataobject.SysUser;
import com.github.jgzl.common.api.vo.SysUserVo;
import com.github.jgzl.common.api.vo.UserVo;
import com.github.jgzl.common.security.exception.BusinessException;
import com.github.jgzl.common.security.util.SecurityUtils;
import com.github.jgzl.infra.upms.convert.SysUserConvert;
import com.github.jgzl.infra.upms.service.SysUserService;
import com.github.jgzl.infra.upms.service.ThirdUserDetailsService;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import me.zhyd.oauth.model.AuthUser;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 用户信息获取
 *
 * @author lihaifeng
 * @date 2020/7/24 17:06
 */
@Service
@AllArgsConstructor
public class ThirdUserDetailsServiceImpl implements ThirdUserDetailsService {

    private final SysUserService userService;

	@Transactional
	@Override
	public UserVo loadUserByUniqueKey(final Authentication authentication) {
		AuthUser authUser = (AuthUser)authentication.getPrincipal();
		String uuid = authUser.getUuid();
		String source = authUser.getSource();
		SysUser sysUser;
		SysUserVo userVo = SecurityUtils.getUser();
		List<SysUser> sysUsers = userService.findUserBySocialUserUuidAndSource(uuid,source);
		if (userVo!=null) {
			if (CollUtil.isNotEmpty(sysUsers)) {
				throw new BusinessException("当前账号已经绑定第三方平台["+source+"]的账户,请解绑后再用第三方平台账户绑定当前登录账号");
			}else {
				sysUser = SysUserConvert.INSTANCE.convert(userVo);
				userService.bindSocialUser(authUser,userVo.getUserId());
			}
		}else {
			if (CollUtil.isEmpty(sysUsers)) {
				sysUser = SysUser.builder()
						.username(source+"_"+uuid)
						.password(RandomUtil.randomStringUpper(32))
						.nickname(authUser.getNickname())
						.avatar(authUser.getAvatar())
						.email(authUser.getEmail())
						.loginTime(LocalDateTime.now())
						.build();
				userService.save(sysUser);
				// 第三方账户创建并绑定用户
				userService.bindSocialUser(authUser,sysUser.getUserId()+"");
			}else {
				sysUser = sysUsers.get(0);
			}
		}
		UserVo user = SysUserConvert.INSTANCE.convertUserDetails(sysUser);
		user.setEnabled(true);
		user.setExpired(false);
		user.setLocked(false);
		user.setPasswordExpired(false);
		user.setRoles(Lists.newArrayList());
		return user;
    }
}
