package com.github.jgzl.application.auth.oauth2.code;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.store.redis.JdkSerializationStrategy;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;
import org.springframework.util.Assert;

import com.github.jgzl.common.core.constant.CacheConstants;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义AuthorizationCodeServices实现类来将auth_code 存放在redis中
 *
 * @author lihaifeng
 * 2019/7/17 10:14
 * @see JdbcAuthorizationCodeServices
 */
@Slf4j
public class RedisAuthenticationCodeServices extends RandomValueAuthorizationCodeServices {

    private static final String AUTH_CODE_KEY = "auth_code";

    private RedisTokenStoreSerializationStrategy serializationStrategy = new JdkSerializationStrategy();

    private static final String PREFIX = CacheConstants.PREFIX + AUTH_CODE_KEY;

    private RedisConnectionFactory connectionFactory;

    private RedisConnection getConnection() {
        return connectionFactory.getConnection();
    }

    public RedisAuthenticationCodeServices(RedisConnectionFactory connectionFactory) {
        Assert.notNull(connectionFactory, "RedisConnectionFactory required");
        this.connectionFactory = connectionFactory;
    }

    @Override
    protected void store(final String code, final OAuth2Authentication authentication) {
        RedisConnection conn = getConnection();
        try {
            conn.hSet(serializationStrategy.serialize(PREFIX),
                    serializationStrategy.serialize(code),
                    serializationStrategy.serialize(authentication));
        } catch (Exception e) {
            log.error("保存authentication code 失败", e);
        } finally {
            conn.close();
        }
    }

    @Override
    protected OAuth2Authentication remove(final String code) {
        RedisConnection conn = getConnection();
        try {
            OAuth2Authentication authentication;
            try {
                authentication = serializationStrategy.deserialize(conn.hGet(serializationStrategy.serialize(PREFIX),
                        serializationStrategy.serialize(code)), OAuth2Authentication.class);
            } catch (Exception e) {
                return null;
            }
            if (null != authentication) {
                conn.hDel(serializationStrategy.serialize(PREFIX), serializationStrategy.serialize(code));
            }
            return authentication;
        } catch (Exception e) {
            return null;
        } finally {
            conn.close();
        }
    }
}
