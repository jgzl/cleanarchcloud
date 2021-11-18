package com.github.jgzl.common.websocket.redis.action;

import cn.hutool.json.JSONObject;
import com.github.jgzl.common.websocket.WebSocketManager;
import com.github.jgzl.common.websocket.utils.WebSocketUtil;

/**
 * {
 * "action":"broadcast",
 * "message":"xxxxxxxxxxxxx"
 * }
 * 广播给所有的websocket发送消息 action
 *
 * @author lihaifeng
 */
public class BroadCastAction implements Action {
    @Override
    public void doMessage(WebSocketManager manager, JSONObject object) {
        if (!object.containsKey(MESSAGE)) {
            return;
        }
        String message = object.getStr(MESSAGE);
        //从本地取出所有的websocket发送消息
        manager.localWebSocketMap().values().forEach(
                webSocket -> WebSocketUtil.sendMessage(
                        webSocket.getSession(), message));
    }
}
