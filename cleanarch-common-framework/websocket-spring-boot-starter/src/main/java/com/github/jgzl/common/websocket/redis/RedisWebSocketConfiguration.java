package com.github.jgzl.common.websocket.redis;

import com.github.jgzl.common.websocket.WebSocketManager;
import com.github.jgzl.common.websocket.configuration.WebSocketHeartBeatChecker;
import com.github.jgzl.common.websocket.configuration.WebSocketProperties;
import com.github.jgzl.common.websocket.redis.action.ActionConfig;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.redisson.api.listener.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @author lihaifeng
 * redis管理websocket配置，利用redis的发布订阅功能实现，具备集群功能
 * 可以扩展此类，添加listener和topic及相应的receiver，使用自己的Enable注解导入即可
 * @see EnableRedisWebSocket
 */
@Slf4j
@Configuration
@Import(ActionConfig.class)
@EnableConfigurationProperties(WebSocketProperties.class)
public class RedisWebSocketConfiguration {

	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		return new ServerEndpointExporter();
	}

	@Bean(WebSocketManager.WEBSOCKET_MANAGER_NAME)
	@ConditionalOnMissingBean(name = WebSocketManager.WEBSOCKET_MANAGER_NAME)
	public WebSocketManager webSocketManager(@Autowired RedissonClient redisson) {
		return new RedisWebSocketManager(redisson);
	}

	@Bean(RedisReceiver.REDIS_RECEIVER_NAME)
	public RedisReceiver redisReceiver(ApplicationContext applicationContext) {
		return new DefaultRedisReceiver(applicationContext);
	}

	@Bean(value = "channelTopic")
	public RTopic channelTopic(RedissonClient redisson, @Qualifier(RedisReceiver.REDIS_RECEIVER_NAME) RedisReceiver redisReceiver) {
		RTopic topic = redisson.getTopic(RedisWebSocketManager.CHANNEL);
		topic.addListener(String.class, new MessageListener<String>() {
			@Override
			public void onMessage(CharSequence channel, String message) {
				log.info("开始处理Redis[{}]接收到的消息", RedisWebSocketManager.CHANNEL);
				redisReceiver.receiveMessage(message);
				log.info("结束处理Redis[{}]接收到的消息", RedisWebSocketManager.CHANNEL);
			}
		});
		return topic;
	}

	@Bean
	@ConditionalOnMissingBean
	public WebSocketHeartBeatChecker webSocketHeartBeatChecker() {
		return new WebSocketHeartBeatChecker();
	}
}