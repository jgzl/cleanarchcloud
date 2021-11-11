package com.github.jgzl.infra.upms.service.impl;

import com.github.jgzl.common.data.mybatis.SuperServiceImpl;
import com.github.jgzl.infra.upms.domain.entity.message.StationMessage;
import com.github.jgzl.infra.upms.repository.StationMessageMapper;
import com.github.jgzl.infra.upms.service.StationMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Levin
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class StationMessageServiceImpl extends SuperServiceImpl<StationMessageMapper, StationMessage> implements StationMessageService {


}
