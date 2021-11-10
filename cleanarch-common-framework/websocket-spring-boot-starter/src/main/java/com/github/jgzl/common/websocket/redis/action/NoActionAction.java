package com.github.jgzl.common.websocket.redis.action;

import cn.hutool.json.JSONObject;
import com.github.jgzl.common.websocket.WebSocketManager;

/**
 * do nothing action
 *
 * @author Levin
 */
public class NoActionAction implements Action {
    @Override
    public void doMessage(WebSocketManager manager, JSONObject object) {
        // do no thing
    }
}
