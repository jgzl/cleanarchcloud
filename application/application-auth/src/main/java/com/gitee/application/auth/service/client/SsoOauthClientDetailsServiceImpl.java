package com.gitee.application.auth.service.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.application.auth.mapper.SsoOauthClientDetailsMapper;
import com.gitee.common.core.constant.CacheConstants;
import com.gitee.common.data.redis.CustomRedisRepository;
import com.gitee.common.security.dao.SsoOauthClientDetailsDAO;
import com.gitee.common.security.exception.BusiException;
import com.gitee.common.security.vo.SsoOauthClientDetailsVO;

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
public class SsoOauthClientDetailsServiceImpl extends ServiceImpl<SsoOauthClientDetailsMapper, SsoOauthClientDetailsDAO> implements SsoOauthClientDetailsService {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private CustomRedisRepository redisRepository;

    @Override
    public SsoOauthClientDetailsVO getVo(final String clientId) {
		final SsoOauthClientDetailsDAO client = this.getOne(Wrappers.<SsoOauthClientDetailsDAO>lambdaQuery()
				.eq(SsoOauthClientDetailsDAO::getClientId, clientId));
        return new SsoOauthClientDetailsVO(client);
    }

    @Override
    public Boolean update(final SsoOauthClientDetailsVO vo) {
        if (StrUtil.isEmpty(vo.getClientId())) {
            throw new BusiException("不存在客户端ID");
        }
        final String key = CacheConstants.REDIS_CLIENTS_PREFIX + vo.getClientId();
        if (redisRepository.exists(key)) {
            if (log.isDebugEnabled()) {
                log.debug("Remove client:{} from redis.", key);
            }
            redisRepository.del(key);
        }
        return this.updateById(new SsoOauthClientDetailsDAO(vo));
    }

    @Override
    public Boolean add(final SsoOauthClientDetailsVO vo) {
        vo.setClientSecret(encoder.encode(vo.getClientSecret()));
        if (StrUtil.isNotEmpty(vo.getClientId())) {
            throw new BusiException("存在客户端ID,无法新增");
        }
        vo.setClientId(vo.getAppName());
        return this.save(new SsoOauthClientDetailsDAO(vo));
    }

    @Override
    public Boolean delete(final String clientId) {
		if (this.getOne(
				Wrappers.<SsoOauthClientDetailsDAO>lambdaQuery().eq(SsoOauthClientDetailsDAO::getClientId, clientId))
				== null) {
			throw new BusiException("不存在的客户端ID: " + clientId);
		}
        final String key = CacheConstants.REDIS_CLIENTS_PREFIX + clientId;
        if (redisRepository.exists(key)) {
            if (log.isDebugEnabled()) {
                log.debug("Remove client:{} from redis.", key);
            }
            redisRepository.del(key);
        }
		return this.remove(Wrappers.<SsoOauthClientDetailsDAO>lambdaQuery()
				.eq(SsoOauthClientDetailsDAO::getClientId, clientId));
    }

    @Override
    public IPage<SsoOauthClientDetailsVO> selectPageVo(final Page page) {
        final IPage<SsoOauthClientDetailsDAO> iPage = this.page(page);
        return iPage.convert(SsoOauthClientDetailsVO::new);
    }
}
