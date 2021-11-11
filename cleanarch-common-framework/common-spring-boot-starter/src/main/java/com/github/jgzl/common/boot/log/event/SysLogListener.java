package com.github.jgzl.common.boot.log.event;

import com.github.jgzl.common.api.feign.tools.RemoteToolsService;
import com.github.jgzl.common.api.vo.OptLogDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;

import java.util.function.Consumer;

/**
 * @author lihaifeng 异步监听日志事件
 */
@Slf4j
@AllArgsConstructor
public class SysLogListener {

	private final RemoteToolsService remoteLogService;

	private final Consumer<OptLogDTO> consumer;

	@Async
	@Order
	@EventListener(SysLogEvent.class)
	public void saveSysLog(SysLogEvent event) {
		OptLogDTO sysLog = (OptLogDTO) event.getSource();
		if (sysLog == null) {
			log.warn("日志为空，忽略操作日志...");
			return;
		}
		consumer.accept(sysLog);
	}

}
