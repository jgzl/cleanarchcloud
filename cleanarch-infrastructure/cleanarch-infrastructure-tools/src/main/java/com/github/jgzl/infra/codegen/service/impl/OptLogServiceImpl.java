package com.github.jgzl.infra.codegen.service.impl;
import com.github.jgzl.common.data.mybatis.SuperServiceImpl;
import com.github.jgzl.infra.codegen.entity.OptLog;
import com.github.jgzl.infra.codegen.mapper.OptLogMapper;
import com.github.jgzl.infra.codegen.service.OptLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
/**
 * @author Levin
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OptLogServiceImpl extends SuperServiceImpl<OptLogMapper, OptLog> implements OptLogService {

}
