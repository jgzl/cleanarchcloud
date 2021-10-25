package com.github.jgzl.common.cache.support;
import org.springframework.context.ApplicationEvent;
import java.time.Clock;

public class RefreshCacheEvent extends ApplicationEvent {
	public RefreshCacheEvent(Object source) {
		super(source);
	}

	public RefreshCacheEvent(Object source, Clock clock) {
		super(source, clock);
	}
}
