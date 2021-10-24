package com.github.jgzl.infra.upms.service.impl;

import com.github.jgzl.infra.upms.mapper.SysOauthClientDetailsMapper;
import com.github.jgzl.infra.upms.service.SysOauthClientDetailsService;
import com.github.jgzl.common.cache.support.CustomRedisRepository;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.api.dataobject.SysOauthClientDetailsDo;
import com.github.jgzl.common.security.exception.BusinessException;
import com.github.jgzl.common.api.vo.SysOauthClientDetailsVo;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 客户端 Service
 *
 * @author lihaifeng
 * 2019/7/5 14:56
 */
@SuppressWarnings("unchecked")
@Service
@Slf4j
@AllArgsConstructor
public class SysOauthClientDetailsServiceImpl extends ServiceImpl<SysOauthClientDetailsMapper, SysOauthClientDetailsDo> implements SysOauthClientDetailsService {

    private PasswordEncoder encoder;

    @Override
    public SysOauthClientDetailsVo getVo(final String clientId) {
		final SysOauthClientDetailsDo client = this.getById(clientId);
		return new SysOauthClientDetailsVo(client);
	}

    @Override
    public Boolean update(final SysOauthClientDetailsVo vo) {
        return this.updateById(new SysOauthClientDetailsDo(vo));
    }

    @Override
    public Boolean add(final SysOauthClientDetailsVo vo) {
        vo.setClientSecret(encoder.encode(vo.getClientSecret()));
        if (StrUtil.isNotEmpty(vo.getClientId())) {
            throw new BusinessException("存在客户端ID,无法新增");
        }
        vo.setClientId(vo.getAppName());
        return this.save(new SysOauthClientDetailsDo(vo));
    }

    @Override
    public IPage<SysOauthClientDetailsVo> selectPageVo(final Page page) {
        final IPage<SysOauthClientDetailsDo> iPage = this.page(page);
        return iPage.convert(SysOauthClientDetailsVo::new);
    }
}
