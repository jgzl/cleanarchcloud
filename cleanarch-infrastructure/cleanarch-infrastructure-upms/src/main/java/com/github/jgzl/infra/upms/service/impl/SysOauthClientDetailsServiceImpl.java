package com.github.jgzl.infra.upms.service.impl;
import com.github.jgzl.infra.upms.mapper.SysOauthClientDetailsMapper;
import com.github.jgzl.infra.upms.service.SysOauthClientDetailsService;
import com.github.jgzl.common.api.dataobject.SysOauthClientDetails;
import com.github.jgzl.common.security.exception.BusinessException;
import com.github.jgzl.common.api.vo.SysOauthClientDetailsVo;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class SysOauthClientDetailsServiceImpl extends ServiceImpl<SysOauthClientDetailsMapper, SysOauthClientDetails> implements SysOauthClientDetailsService {

    private PasswordEncoder encoder;

    @Override
    public SysOauthClientDetailsVo getVo(final String clientId) {
		final SysOauthClientDetails client = this.getById(clientId);
		return new SysOauthClientDetailsVo(client);
	}

    @Override
    public Boolean update(final SysOauthClientDetailsVo vo) {
        return this.updateById(new SysOauthClientDetails(vo));
    }

    @Override
    public Boolean add(final SysOauthClientDetailsVo vo) {
        vo.setClientSecret(encoder.encode(vo.getClientSecret()));
        if (StrUtil.isNotEmpty(vo.getClientId())) {
            throw new BusinessException("存在客户端ID,无法新增");
        }
        vo.setClientId(vo.getAppName());
        return this.save(new SysOauthClientDetails(vo));
    }

    @Override
    public IPage<SysOauthClientDetailsVo> selectPageVo(final Page page) {
        final IPage<SysOauthClientDetails> iPage = this.page(page);
        return iPage.convert(SysOauthClientDetailsVo::new);
    }
}
