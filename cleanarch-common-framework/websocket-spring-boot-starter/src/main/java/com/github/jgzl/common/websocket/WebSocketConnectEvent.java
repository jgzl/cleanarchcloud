package com.github.jgzl.common.websocket;

import org.springframework.context.ApplicationEvent;

/**
 * @author Levin
 */
public class WebSocketConnectEvent extends ApplicationEvent {
    public WebSocketConnectEvent(WebSocket webSocket) {
        super(webSocket);
    }
}
