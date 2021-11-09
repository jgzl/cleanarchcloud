package com.github.jgzl.common.log.event;
import com.github.jgzl.common.api.vo.SysLogDTO;
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
