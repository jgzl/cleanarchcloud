//package com.github.jgzl.common.security.component;
//
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.security.oauth2.provider.token.store.redis.StandardStringSerializationStrategy;
///**
// * @author lihaifeng
// */
//public class ExtendStringSerializationStrategy extends StandardStringSerializationStrategy {
//	private static final GenericJackson2JsonRedisSerializer OBJECT_SERIALIZER = new GenericJackson2JsonRedisSerializer();
//	@Override
//	protected <T> T deserializeInternal(byte[] bytes, Class<T> clazz) {
//		return (T) OBJECT_SERIALIZER.deserialize(bytes);
//	}
//	@Override
//	protected byte[] serializeInternal(Object object) {
//		return OBJECT_SERIALIZER.serialize(object);
//	}
//}
