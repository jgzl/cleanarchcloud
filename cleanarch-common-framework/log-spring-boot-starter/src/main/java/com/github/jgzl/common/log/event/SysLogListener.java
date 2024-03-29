package com.github.jgzl.common.log.event;

import com.github.jgzl.common.core.constant.SecurityConstants;
import com.github.jgzl.infra.upms.api.dto.SysLogDTO;
import com.github.jgzl.infra.upms.api.feign.RemoteLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

/**
 * @author lihaifeng 异步监听日志事件
 */
@Slf4j
@AllArgsConstructor
public class SysLogListener {

	private final RemoteLogService remoteLogService;

	@Async
	@Order
	@EventListener(SysLogEvent.class)
	public void saveSysLog(SysLogEvent event) {
		SysLogDTO sysLog = event.getSysLog();
		remoteLogService.saveLog(sysLog, SecurityConstants.FROM_IN);
	}

}
