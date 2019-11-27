package com.dlihaifeng.conversion.platform.application.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;

import com.dlihaifeng.conversion.platform.application.ws.handler.HttpAuthHandler;
import com.dlihaifeng.conversion.platform.application.ws.interceptor.HttpAuthInterceptor;

import lombok.extern.slf4j.Slf4j;

/**
 * @author lihaifeng
 */
@Slf4j
@Component
public class RedisMessageListener extends MessageListenerAdapter {

  @Value("${redis.topic.chat}")
  private String chatTopic;

  @Autowired
  private StringRedisTemplate redisTemplate;

  @Autowired
  private HttpAuthHandler httpAuthHandler;

  @Autowired
  private HttpAuthInterceptor httpAuthInterceptor;

  @Override
  public void onMessage(Message message, byte[] pattern) {
    byte[] body = message.getBody();
    byte[] channel = message.getChannel();
    String msg;
    String topic;
    try {
      msg = redisTemplate.getStringSerializer().deserialize(body);
      topic = redisTemplate.getStringSerializer().deserialize(channel);
      log.info("Received raw message from topic:" + topic + ", raw message content：" + msg);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      return;
    }

    if (chatTopic.equals(topic)) {
      try {
        String userId;
        if (msg.contains("lihaifeng")) {
          userId = "lihaifeng";
        } else if (msg.contains("wuzhiyuan")) {
          userId = "wuzhiyuan";
        } else {
          userId = "#everyone#";
        }
        httpAuthHandler.sendToUser(userId, new TextMessage(msg));
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      log.warn("No further operation with this topic!");
    }
  }
}
