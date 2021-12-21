package com.github.jgzl.infra.oauth.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.core.constant.SecurityConstants;
import com.github.jgzl.common.core.util.KeyStrResolver;
import com.github.jgzl.common.core.util.R;
import com.github.jgzl.common.core.util.SpringContextHolder;
import com.github.jgzl.infra.upms.api.entity.SysOauthClientDetails;
import com.github.jgzl.infra.upms.api.feign.RemoteClientDetailsService;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.event.LogoutSuccessEvent;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lihaifeng
 * @date 2020/9/3
 * <p>
 * token 端点相关的业务处理
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExtendTokenDealServiceImpl {

	private final RedissonClient redisson;

	private final CacheManager cacheManager;

	private final TokenStore tokenStore;

	private final KeyStrResolver keyStrResolver;

	private final RemoteClientDetailsService clientDetailsService;

	/**
	 * 删除 请求令牌 和 刷新令牌
	 *
	 * @param token 请求令牌
	 * @return R
	 */
	public R<Boolean> removeToken(String token) {

		OAuth2AccessToken accessToken = tokenStore.readAccessToken(token);
		if (accessToken == null || StrUtil.isBlank(accessToken.getValue())) {
			return R.ok(Boolean.TRUE, "退出失败，token 无效");
		}

		OAuth2Authentication auth2Authentication = tokenStore.readAuthentication(accessToken);
		// 清空用户信息
		cacheManager.getCache(CacheConstants.USER_DETAILS).evict(auth2Authentication.getName());

		// 清空access token
		tokenStore.removeAccessToken(accessToken);

		// 清空 refresh token
		OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
		tokenStore.removeRefreshToken(refreshToken);

		// 发送退出成功事件
		SpringContextHolder.publishEvent(new LogoutSuccessEvent(auth2Authentication));
		return R.ok();
	}

	/**
	 * 根据用户名查询 令牌相关信息
	 *
	 * @param page     分页信息
	 * @param username 用户名
	 * @return R
	 */
	public R queryTokenByUsername(Page page, String username) {
		List<OAuth2AccessToken> oAuth2AccessTokenList = clientDetailsService
				.listClientDetails(SecurityConstants.FROM_IN).getData().stream().map(SysOauthClientDetails::getClientId)
				.flatMap(clientId -> tokenStore.findTokensByClientIdAndUserName(clientId, username).stream()).distinct()
				.collect(Collectors.toList());
		page.setRecords(oAuth2AccessTokenList);
		page.setTotal(oAuth2AccessTokenList.size());
		return R.ok(page);
	}

	/**
	 * 分页查询token 列表
	 *
	 * @param page page
	 * @return
	 */
	public R<Page> queryToken(Page page) {
		// 根据分页参数获取对应数据
		String key = keyStrResolver.extract(SecurityConstants.PREFIX + SecurityConstants.OAUTH_PREFIX + "access:*",
				StrUtil.COLON);
		long pageNum = page.getCurrent();
		long pageSize = page.getSize();
		RKeys keys = redisson.getKeys();
		List<String> allKeyList = Lists.newArrayList();
		while (true) {
			Iterable<String> pages = keys.getKeysWithLimit(key, 1000);
			if (CollUtil.isEmpty(pages)) {
				break;
			} else {
				Iterator<String> iterator = pages.iterator();
				if (iterator.hasNext()) {
					allKeyList.add(iterator.next());
				}
			}
		}
		List<String> pageKeyList = allKeyList.subList((int) ((pageNum - 1) * pageSize), (int) (pageNum * pageSize));
		Map<String, Object> resultMap = redisson.getBuckets().get(ArrayUtil.toArray(pageKeyList, String.class));
		page.setRecords(Lists.newArrayList(resultMap.values()));
		page.setTotal(allKeyList.size());
		return R.ok(page);
	}

}
