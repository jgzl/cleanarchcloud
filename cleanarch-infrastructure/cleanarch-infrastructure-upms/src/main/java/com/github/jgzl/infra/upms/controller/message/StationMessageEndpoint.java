package com.github.jgzl.infra.upms.controller.message;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.github.jgzl.common.core.util.SpringContextHolder;
import com.github.jgzl.common.data.configuration.dynamic.TenantDynamicDataSourceProcess;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.common.data.properties.DatabaseProperties;
import com.github.jgzl.common.data.properties.MultiTenantType;
import com.github.jgzl.common.websocket.BaseWebSocketEndpoint;
import com.github.jgzl.infra.upms.domain.entity.message.StationMessage;
import com.github.jgzl.infra.upms.service.StationMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.List;


/**
 * 聊天室
 *
 * @author Levin
 * @since 2020/11/11
 */
@Slf4j
@Component
@ServerEndpoint(value = "/message/{tenantCode}/{identifier}")
public class StationMessageEndpoint extends BaseWebSocketEndpoint {

    @OnOpen
    public void openSession(@PathParam("tenantCode") String tenantCode, @PathParam(IDENTIFIER) String userId, Session session) {
        connect(userId, session);
        List<StationMessage> messages;
        final DatabaseProperties properties = SpringContextHolder.getBean(DatabaseProperties.class);
        final StationMessageService stationMessageService = SpringContextHolder.getBean(StationMessageService.class);
        if (properties.getMultiTenant().getType() == MultiTenantType.DATASOURCE) {
            final TenantDynamicDataSourceProcess dataSourceProcess = SpringContextHolder.getBean(TenantDynamicDataSourceProcess.class);
            final String dsKey = dataSourceProcess.buildDb(tenantCode);
            DynamicDataSourceContextHolder.push(dsKey);
            log.debug("设置当前线程数据源 - {}", dsKey);
            messages = stationMessageService.list(Wraps.<StationMessage>lbQ().eq(StationMessage::getMark, false)
                    .eq(StationMessage::getReceiveId, userId).orderByAsc(StationMessage::getId));
            DynamicDataSourceContextHolder.poll();
            log.debug("清空当前线程数据源...");
        } else {
            messages = stationMessageService.list(Wraps.<StationMessage>lbQ().eq(StationMessage::getMark, false)
                    .eq(StationMessage::getReceiveId, userId).orderByAsc(StationMessage::getId));
        }
        if (CollectionUtil.isEmpty(messages)) {
            return;
        }
        messages.forEach(message -> senderMessage(userId, JSON.toJSONString(message)));
    }

    @OnMessage
    public void onMessage(@PathParam(IDENTIFIER) String userId, Session session, String message) {
        log.info("接收到的消息" + message);
    }

    @OnClose
    public void onClose(@PathParam(IDENTIFIER) String userId, Session session) {
        disconnect(userId);
    }

    @OnError
    public void onError(@PathParam(IDENTIFIER) String userId, Session session, Throwable throwable) {
        log.info("发生异常：, identifier {} ", userId);
        log.error(throwable.getMessage(), throwable);
        disconnect(userId);
    }

}