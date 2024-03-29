package com.github.jgzl.common.websocket.redis.action;

import cn.hutool.json.JSONObject;
import com.github.jgzl.common.websocket.WebSocketManager;

/**
 * 要做的事情
 *
 * @author lihaifeng
 */
public interface Action {

	String IDENTIFIER = "identifier";
	String MESSAGE = "message";
	String ACTION = "action";

	/**
	 * 根据消息做自己的事情
	 *
	 * @param manager webSocket管理器
	 * @param object  消息体转化的JSON
	 */
	void doMessage(WebSocketManager manager, JSONObject object);
}
