package com.github.jgzl.common.log.event;

import com.github.jgzl.infra.upms.api.dto.SysLogDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lihaifeng 系统日志事件
 */
@Getter
@AllArgsConstructor
public class SysLogEvent {

	private final SysLogDTO sysLog;

}
