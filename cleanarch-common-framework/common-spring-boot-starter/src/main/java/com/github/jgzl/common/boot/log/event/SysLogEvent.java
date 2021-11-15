package com.github.jgzl.common.boot.log.event;
import com.github.jgzl.common.api.vo.OptLogDTO;
import org.springframework.context.ApplicationEvent;
/**
 * @author lihaifeng 系统日志事件
 */
public class SysLogEvent extends ApplicationEvent {
	public SysLogEvent(OptLogDTO source) {
		super(source);
	}
}
