package com.gitee.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

@Configuration
public class RedisConfiguration {

	@Value("${redis.topic.chat}")
	private String chatTopic;

	@Bean
	RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
			MessageListener messageListener) {
		RedisMessageListenerContainer redisMessageListenerContainer = new RedisMessageListenerContainer();
		redisMessageListenerContainer.setConnectionFactory(connectionFactory);
		redisMessageListenerContainer.addMessageListener(messageListener, new PatternTopic(chatTopic));
		return redisMessageListenerContainer;
	}
}
