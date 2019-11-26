package com.dlihaifeng.conversion.platform.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import com.dlihaifeng.conversion.platform.application.ws.handler.HttpAuthHandler;
import com.dlihaifeng.conversion.platform.application.ws.interceptor.HttpAuthInterceptor;

/**
 * @author lihaifeng
 * 一共有四种方式，三种spring websocket starter,还有一种付费文档tio
 * https://www.cnblogs.com/kiwifly/p/11729304.html
 *
 * Session 共享的问题
 * 上面反复提到一个问题就是，服务端如果要主动发送消息给客户端一定要用到 session。
 * 而大家都知道的是 session 这个东西是不跨 jvm 的。如果有多台服务器，在 http 请求的情况下，
 * 我们可以通过把 session 放入缓存中间件中来共享解决这个问题，通过 spring session 几条配置就解决了。
 * 但是 web socket  不可以。他的 session 是不能序列化的，当然这样设计的目的不是为了为难你，而是出于对 http 与 web socket 请求的差异导致的。
 * 目前网上找到的最简单方案就是通过 redis 订阅广播的形式，主要代码跟第二种方式差不多，
 * 你要在本地放个 map 保存请求的 session。也就是说每台服务器都会保存与他连接的 session 于本地。
 * 然后发消息的地方要修改，并不是现在这样直接发送，而通过 redis 的订阅机制。服务器要发消息的时候，
 * 你通过 redis 广播这条消息，所有订阅的服务端都会收到这个消息，然后本地尝试发送。最后肯定只有有这个对应用户 session 的那台才能发送出去。
 */
@Configuration
@EnableWebSocket
public class WebSocketConfiguration implements WebSocketConfigurer {

  @Autowired
  private HttpAuthHandler httpAuthHandler;

  @Autowired
  private HttpAuthInterceptor httpAuthInterceptor;

  @Override
  public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
    registry
        .addHandler(httpAuthHandler, "ws")
        .addInterceptors(httpAuthInterceptor)
        .setAllowedOrigins("*");
  }
}
