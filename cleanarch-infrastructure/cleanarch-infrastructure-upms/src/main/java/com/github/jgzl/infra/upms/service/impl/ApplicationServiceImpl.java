package com.github.jgzl.infra.upms.service.impl;

import com.github.jgzl.common.data.mybatis.SuperServiceImpl;
import com.github.jgzl.infra.upms.domain.entity.baseinfo.OAuthClientDetails;
import com.github.jgzl.infra.upms.repository.OAuthClientDetailsMapper;
import com.github.jgzl.infra.upms.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author lihaifeng
 */
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl extends SuperServiceImpl<OAuthClientDetailsMapper, OAuthClientDetails> implements ApplicationService {


}
