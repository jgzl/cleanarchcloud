package com.github.jgzl.infra.upms.login.service;

import cn.hutool.core.util.StrUtil;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.core.util.StringUtils;
import com.github.jgzl.common.security.exception.Auth2Exception;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.OAuthClientDetails;
import com.github.jgzl.infra.upms.repository.OAuthClientDetailsMapper;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 自定义 redis 缓存实现的 ClientDetailsService
 *
 * @author lihaifeng
 */
@Service
@Primary
@RequiredArgsConstructor
public class CacheClientDetailsServiceImpl implements ClientDetailsService {

    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private OAuthClientDetailsMapper oAuthClientDetailsMapper;

    @SneakyThrows
    @Override
	@Cacheable(value = CacheConstants.CLIENT_DETAILS_KEY, key = "#clientId", unless = "#result == null")
	public ClientDetails loadClientByClientId(String clientId) {
        final OAuthClientDetails details = this.oAuthClientDetailsMapper.selectById(clientId);
        if (Objects.isNull(details)) {
            throw new Auth2Exception("client_id 或 client_secret 错误", HttpStatus.BAD_REQUEST.value());
        }
        if (!details.getStatus()) {
            throw new Auth2Exception("client_id 已被禁用", HttpStatus.BAD_REQUEST.value());
        }
        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(details.getClientId());
        clientDetails.setClientSecret(passwordEncoder.encode(details.getClientSecret()));
        clientDetails.setAccessTokenValiditySeconds(details.getAccessTokenValidity());
        clientDetails.setRefreshTokenValiditySeconds(details.getRefreshTokenValidity());
        if (StringUtils.isNotBlank(details.getResourceIds())) {
            clientDetails.setResourceIds(StrUtil.split(details.getResourceIds(), ','));
        }
        if (StringUtils.isNotBlank(details.getAuthorizedGrantTypes())) {
            clientDetails.setAuthorizedGrantTypes(StrUtil.split(details.getAuthorizedGrantTypes(), ','));
        }
        if (StringUtils.isNotBlank(details.getScope())) {
            clientDetails.setScope(StrUtil.split(details.getScope(), ','));
        }
        if (StringUtils.isNotBlank(details.getAutoApprove())) {
            clientDetails.setAutoApproveScopes(StrUtil.split(details.getAutoApprove(), ','));
        }
        if (StringUtils.isNotBlank(details.getWebServerRedirectUri())) {
            final HashSet<String> registeredRedirectUris = Sets.newHashSet(StrUtil.split(details.getWebServerRedirectUri(), ','));
            clientDetails.setRegisteredRedirectUri(registeredRedirectUris);
        }
        if (StringUtils.isNotBlank(details.getAuthorities())) {
            List<SimpleGrantedAuthority> authorities = StrUtil.split(details.getAuthorities(), ',')
                    .stream().distinct().map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            clientDetails.setAuthorities(authorities);
        }
        return clientDetails;
    }

}
