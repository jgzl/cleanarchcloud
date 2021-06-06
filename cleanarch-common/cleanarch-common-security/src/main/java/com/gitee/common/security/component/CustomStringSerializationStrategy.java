package com.gitee.common.security.component;

import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.security.oauth2.provider.token.store.redis.StandardStringSerializationStrategy;

/**
 * @author lihaifeng
 */
public class CustomStringSerializationStrategy extends StandardStringSerializationStrategy {

	private static final Jackson2JsonRedisSerializer OBJECT_SERIALIZER = new Jackson2JsonRedisSerializer(Object.class);

	@Override
	protected <T> T deserializeInternal(byte[] bytes, Class<T> clazz) {
		return (T) OBJECT_SERIALIZER.deserialize(bytes);
	}

	@Override
	protected byte[] serializeInternal(Object object) {
		return OBJECT_SERIALIZER.serialize(object);
	}
}
