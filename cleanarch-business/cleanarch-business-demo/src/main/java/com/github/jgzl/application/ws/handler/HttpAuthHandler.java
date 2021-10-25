package com.github.jgzl.application.ws.handler;
import java.io.IOException;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import cn.hutool.core.lang.Assert;
import lombok.extern.slf4j.Slf4j;

/**
 * @author lihaifeng
 * 说明
 * 通过继承 TextWebSocketHandler 类并覆盖相应方法，可以对 websocket 的事件进行处理，这里可以同原生注解的那几个注解连起来看
 *
 * afterConnectionEstablished 方法是在 socket 连接成功后被触发，同原生注解里的 @OnOpen 功能
 * afterConnectionClosed  方法是在 socket 连接关闭后被触发，同原生注解里的 @OnClose 功能
 * handleTextMessage 方法是在客户端发送信息时触发，同原生注解里的 @OnMessage 功能
 *
 * WsSessionManager
 */
@Slf4j
@Component
public class HttpAuthHandler extends TextWebSocketHandler {

	private static final String P_ANYONE = "#anyone#";

	private static final String P_EVERYONE = "#everyone#";

	/**
	 * socket 建立成功事件
	 *
	 * @param session
	 * @throws Exception
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Object token = session.getAttributes().get("token");
		if (token != null) {
			// 用户连接成功，放入在线用户缓存
			WsSessionManager.add(token.toString(), session);
		} else {
			throw new RuntimeException("用户登录已经失效!");
		}
	}

	/**
	 * 接收消息事件
	 *
	 * @param session
	 * @param message
	 * @throws Exception
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 获得客户端传来的消息
		String payload = message.getPayload();
		String token = (String) session.getAttributes().get("token");
		log.info("server 接收到 " + token + " 发送的 " + payload);
		//单独发送给某一个人
		sendToUser(token, message);
	}

	/**
	 * socket 断开连接时
	 *
	 * @param session
	 * @param status
	 * @throws Exception
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		Object token = session.getAttributes().get("token");
		if (token != null) {
			// 用户退出，移除缓存
			WsSessionManager.remove(token.toString());
		}
	}

	public void sendToUser(String userId, TextMessage message) {
		Assert.notNull(userId);
		Assert.notNull(message);
		Assert.notNull(message.getPayload());
		// 获得客户端传来的消息
		String payload = message.getPayload();
		if (payload.startsWith(P_EVERYONE)) {
			userId = P_EVERYONE;
		}
		String finalUserId = userId;
		WsSessionManager.SESSION_POOL.keySet().forEach(id -> {
			if (P_EVERYONE.equals(finalUserId) || id.equals(finalUserId)) {
				try {
					log.debug("需要发送给{}", id);
					WebSocketSession webSocketSession = WsSessionManager.SESSION_POOL.get(id);
					if (webSocketSession.isOpen()) {
						webSocketSession
								.sendMessage(message);
					}
				} catch (IOException e) {
					log.error("发送websocket消息发生异常:", e);
				}
			}
		});
	}
}
