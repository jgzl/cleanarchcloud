package com.github.jgzl.application.auth.service.client;

import com.github.jgzl.application.auth.mapper.SysOauthClientDetailsMapper;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.data.cache.CustomRedisRepository;
import com.github.jgzl.common.security.dataobject.SysOauthClientDetailsDo;
import com.github.jgzl.common.security.exception.BusinessException;
import com.github.jgzl.common.security.vo.SysOauthClientDetailsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * 客户端 Service
 *
 * @author lihaifeng
 * 2019/7/5 14:56
 */
@SuppressWarnings("unchecked")
@Service
@Slf4j
public class SysOauthClientDetailsServiceImpl extends ServiceImpl<SysOauthClientDetailsMapper, SysOauthClientDetailsDo> implements SysOauthClientDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private CustomRedisRepository redisRepository;

    @Override
    public SysOauthClientDetailsVo getVo(final String clientId) {
		final SysOauthClientDetailsDo client = this.getById(clientId);
		return new SysOauthClientDetailsVo(client);
	}

    @Override
    public Boolean update(final SysOauthClientDetailsVo vo) {
        if (StrUtil.isEmpty(vo.getClientId())) {
            throw new BusinessException("不存在客户端ID");
        }
        final String key = CacheConstants.REDIS_CLIENTS_PREFIX + vo.getClientId();
        if (redisRepository.exists(key)) {
            if (log.isDebugEnabled()) {
                log.debug("Remove client:{} from redis.", key);
            }
            redisRepository.del(key);
        }
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
    public Boolean delete(final String clientId) {
		if (this.getById(clientId) == null) {
			throw new BusinessException("不存在的客户端ID: " + clientId);
		}
		final String key = CacheConstants.REDIS_CLIENTS_PREFIX + clientId;
		if (redisRepository.exists(key)) {
			if (log.isDebugEnabled()) {
				log.debug("Remove client:{} from redis.", key);
			}
			redisRepository.del(key);
		}
		return this.removeById(clientId);
	}

    @Override
    public IPage<SysOauthClientDetailsVo> selectPageVo(final Page page) {
        final IPage<SysOauthClientDetailsDo> iPage = this.page(page);
        return iPage.convert(SysOauthClientDetailsVo::new);
    }
}
