package com.github.jgzl.common.websocket.memory;

import com.github.jgzl.common.websocket.WebSocketManager;
import com.github.jgzl.common.websocket.configuration.WebSocketHeartBeatChecker;
import com.github.jgzl.common.websocket.configuration.WebSocketProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 内存管理websocket配置
 *
 * @author lihaifeng
 */
@Configuration
@EnableConfigurationProperties(WebSocketProperties.class)
public class MemoryWebSocketConfiguration {
	@Bean(WebSocketManager.WEBSOCKET_MANAGER_NAME)
	@ConditionalOnMissingBean(name = WebSocketManager.WEBSOCKET_MANAGER_NAME)
	public WebSocketManager webSocketManager() {
		return new MemWebSocketManager();
	}

	@Bean
	@ConditionalOnMissingBean
	public WebSocketHeartBeatChecker webSocketHeartBeatChecker() {
		return new WebSocketHeartBeatChecker();
	}
}