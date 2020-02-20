package com.gitee.common.data.redis;

import java.time.Duration;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import io.lettuce.core.ReadFrom;
import io.lettuce.core.cluster.ClusterClientOptions;
import io.lettuce.core.cluster.ClusterTopologyRefreshOptions;
import lombok.AllArgsConstructor;

/**
 * @author lihaifeng
 */
@EnableCaching
@Configuration
@AllArgsConstructor
@AutoConfigureBefore(RedisAutoConfiguration.class)
public class RedisTemplateConfiguration {
	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.setHashKeySerializer(new StringRedisSerializer());
		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
		redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		return redisTemplate;
	}

	@Bean
	@ConditionalOnProperty(value = "spring.redis.cluster.enable", havingValue = "true")
	public LettuceConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
		RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(
				redisProperties.getCluster().getNodes());

		// https://github.com/lettuce-io/lettuce-core/wiki/Redis-Cluster#user-content-refreshing-the-cluster-topology-view
		ClusterTopologyRefreshOptions clusterTopologyRefreshOptions = ClusterTopologyRefreshOptions.builder()
				.enablePeriodicRefresh()
				.enableAllAdaptiveRefreshTriggers()
				.refreshPeriod(Duration.ofSeconds(5))
				.build();

		ClusterClientOptions clusterClientOptions = ClusterClientOptions.builder()
				.topologyRefreshOptions(clusterTopologyRefreshOptions).build();

		// https://github.com/lettuce-io/lettuce-core/wiki/ReadFrom-Settings
		LettuceClientConfiguration lettuceClientConfiguration = LettuceClientConfiguration.builder()
				.readFrom(ReadFrom.REPLICA_PREFERRED)
				.clientOptions(clusterClientOptions).build();

		return new LettuceConnectionFactory(redisClusterConfiguration, lettuceClientConfiguration);
	}
}
