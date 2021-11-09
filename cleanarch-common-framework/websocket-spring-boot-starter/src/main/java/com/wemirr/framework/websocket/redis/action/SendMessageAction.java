package com.wemirr.framework.websocket.redis.action;

import cn.hutool.json.JSONObject;
import com.wemirr.framework.websocket.WebSocket;
import com.wemirr.framework.websocket.WebSocketManager;
import com.wemirr.framework.websocket.utils.WebSocketUtil;

/**
 * {
 * "action":"sendMessage",
 * "identifier":"xxx",
 * "message":"xxxxxxxxxxx"
 * }
 * 给webSocket发送消息的action
 *
 * @author Levin
 */
public class SendMessageAction implements Action {
    @Override
    public void doMessage(WebSocketManager manager, JSONObject object) {
        if (!object.containsKey(IDENTIFIER)) {
            return;
        }
        if (!object.containsKey(MESSAGE)) {
            return;
        }

        String identifier = object.getStr(IDENTIFIER);

        WebSocket webSocket = manager.get(identifier);
        if (null == webSocket) {
            return;
        }
        WebSocketUtil.sendMessage(webSocket.getSession(), object.getStr(MESSAGE));
    }
}
