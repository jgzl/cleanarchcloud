package com.github.jgzl.common.websocket.redis;

import cn.hutool.json.JSONObject;
import com.github.jgzl.common.websocket.WebSocket;
import com.github.jgzl.common.websocket.memory.MemWebSocketManager;
import com.github.jgzl.common.websocket.redis.action.Action;
import com.github.jgzl.common.websocket.redis.action.BroadCastAction;
import com.github.jgzl.common.websocket.redis.action.RemoveAction;
import com.github.jgzl.common.websocket.redis.action.SendMessageAction;
import com.github.jgzl.common.websocket.utils.WebSocketUtil;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

/**
 * WebSocket的session无法序列化,所以session还是保存在本地内存中，发送消息这种就走订阅发布模式
 * 1.redis或者mq进行发布订阅，广播->有某个节点能找到此人就发送消息，其他的忽略
 * 2.Nginx 进行IP hash 可以使用{@link MemWebSocketManager}
 * <p>
 * 3.需要扩展不同的功能,就写相应的Action,放入容器中,然后给订阅的频道发布一条包含该Action的JSON串
 *
 * @author lihaifeng
 * @see RedisWebSocketManager#sendMessage
 */
public class RedisWebSocketManager extends MemWebSocketManager {

    public static final String CHANNEL = "websocket";
    private static final String COUNT_KEY = "RedisWebSocketManagerCountKey";
    protected RedissonClient redisson;

    public RedisWebSocketManager(RedissonClient redisson) {
        this.redisson = redisson;
    }


    @Override
    public void put(String identifier, WebSocket webSocket) {
        super.put(identifier, webSocket);
        //在线数量加1
        countChange(1);
    }

    @Override
    public void remove(String identifier) {
        boolean containsKey = localWebSocketMap().containsKey(identifier);
        if (containsKey) {
            super.remove(identifier);
        } else {
            JSONObject map = new JSONObject();
            map.set(Action.ACTION, RemoveAction.class.getName());
            map.set(Action.IDENTIFIER, identifier);
            //在websocket频道上发布发送消息的消息
            redisson.getTopic(getChannel()).publish(map.toString());
        }
        //在线数量减1
        countChange(-1);
    }

    @Override
    public int size() {
        return getCount();
    }

    @Override
    public void sendMessage(String identifier, String message) {
        WebSocket webSocket = get(identifier);
        //本地能找到就直接发
        if (null != webSocket) {
            WebSocketUtil.sendMessage(webSocket.getSession(), message);
            return;
        }


        JSONObject map = new JSONObject();
        map.put(Action.ACTION, SendMessageAction.class.getName());
        map.put(Action.IDENTIFIER, identifier);
        map.put(Action.MESSAGE, message);
        //在websocket频道上发布发送消息的消息
		redisson.getTopic(getChannel()).publish(map.toString());
    }

    @Override
    public void broadcast(String message) {
        JSONObject map = new JSONObject();
        map.put(Action.ACTION, BroadCastAction.class.getName());
        map.put(Action.MESSAGE, message);
        //在websocket频道上发布广播的消息
		redisson.getTopic(getChannel()).publish(map.toString());
    }

    protected String getChannel() {
        return CHANNEL;
    }

    /**
     * 增减在线数量
     */
    private void countChange(int delta) {
		RBucket<Object> bucket = redisson.getBucket(COUNT_KEY);
		//获取在线当前数量
		String countStr = (String) bucket.get();
		int count = 0;
		if (null != countStr) {
			count = Integer.parseInt(countStr);
		}

        count = count + delta;
        count = Math.max(count, 0);

        //设置新的数量
		bucket.set("" + count);
    }

    /**
     * 获取当前在线数量
     */
    private int getCount() {
		RBucket<Object> bucket = redisson.getBucket(COUNT_KEY);
		//获取在线当前数量
		String countStr = (String) bucket.get();
		int count = 0;
		if (null != countStr) {
			count = Integer.parseInt(countStr);
		}
		return count;
    }

}
