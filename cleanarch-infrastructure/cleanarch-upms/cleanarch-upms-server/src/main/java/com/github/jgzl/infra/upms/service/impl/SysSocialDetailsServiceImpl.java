package com.github.jgzl.infra.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.security.util.SecurityUtils;
import com.github.jgzl.infra.upms.api.dto.UserInfo;
import com.github.jgzl.infra.upms.api.entity.SysSocialDetails;
import com.github.jgzl.infra.upms.api.entity.SysUser;
import com.github.jgzl.infra.upms.handler.LoginHandler;
import com.github.jgzl.infra.upms.mapper.SysSocialDetailsMapper;
import com.github.jgzl.infra.upms.mapper.SysUserMapper;
import com.github.jgzl.infra.upms.service.SysSocialDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lihaifeng
 * @date 2018年08月16日
 */
@Slf4j
@AllArgsConstructor
@Service("sysSocialDetailsService")
public class SysSocialDetailsServiceImpl extends ServiceImpl<SysSocialDetailsMapper, SysSocialDetails>
		implements SysSocialDetailsService {

	private final Map<String, LoginHandler> loginHandlerMap;

	private final CacheManager cacheManager;

	private final SysUserMapper sysUserMapper;

	/**
	 * 绑定社交账号
	 *
	 * @param type type
	 * @param code code
	 * @return
	 */
	@Override
	public Boolean bindSocial(String type, String code) {
		LoginHandler loginHandler = loginHandlerMap.get(type);
		// 绑定逻辑
		String identify = loginHandler.identify(code);
		SysUser sysUser = sysUserMapper.selectById(SecurityUtils.getUser().getId());
		loginHandler.bind(sysUser, identify);

		// 更新緩存
		cacheManager.getCache(CacheConstants.USER_DETAILS).evict(sysUser.getUsername());
		return Boolean.TRUE;
	}

	/**
	 * 根据入参查询用户信息
	 *
	 * @param inStr TYPE@code
	 * @return
	 */
	@Override
	public UserInfo getUserInfo(String inStr) {
		String[] inStrs = inStr.split(StringPool.AT);
		String type = inStrs[0];
		String loginStr = inStrs[1];
		return loginHandlerMap.get(type).handle(loginStr);
	}

}
