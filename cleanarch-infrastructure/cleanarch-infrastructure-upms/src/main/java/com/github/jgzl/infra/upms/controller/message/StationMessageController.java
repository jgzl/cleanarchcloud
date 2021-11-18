package com.github.jgzl.infra.upms.controller.message;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.data.TenantEnvironment;
import com.github.jgzl.common.data.configuration.dynamic.ann.DynamicDS;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.common.data.page.PageRequest;
import com.github.jgzl.infra.upms.domain.entity.message.StationMessage;
import com.github.jgzl.infra.upms.service.StationMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author lihaifeng
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@DynamicDS
public class StationMessageController {

    private final TenantEnvironment tenantEnvironment;
    private final StationMessageService stationMessageService;

    @GetMapping("/station_messages/my")
    public Result<Page<StationMessage>> myMessage(String title, String level, Boolean mark, PageRequest request) {
        final Page<StationMessage> page = stationMessageService.page(request.buildPage(), Wraps.<StationMessage>lbQ()
                .like(StationMessage::getTitle, title).eq(StationMessage::getLevel, level)
                .eq(StationMessage::getMark, mark).eq(StationMessage::getReceiveId, tenantEnvironment.userId()));
        return Result.success(page);
    }

    @GetMapping("/users/{user_id}/station_messages")
    public Result<List<StationMessage>> load(@PathVariable("user_id") Long userId) {
        final List<StationMessage> messages = this.stationMessageService.list(Wraps.<StationMessage>lbQ()
                .eq(StationMessage::getMark, false)
                .eq(StationMessage::getReceiveId, userId));
        return Result.success(messages);
    }

    @PatchMapping("/station_messages/{message_id}/mark")
    public Result<ResponseEntity<Void>> mark(@PathVariable("message_id") Long messageId) {
        StationMessage message = new StationMessage();
        message.setId(messageId);
        message.setMark(true);
        this.stationMessageService.updateById(message);
        return Result.success();
    }


    @DeleteMapping("/station_messages/{message_id}")
    public Result<ResponseEntity<Void>> del(@PathVariable("message_id") Long messageId) {
        this.stationMessageService.removeById(messageId);
        return Result.success();
    }

    @DeleteMapping("/station_messages")
    public Result<ResponseEntity<Void>> batchDel(@RequestBody List<Long> ids) {
        this.stationMessageService.removeByIds(ids);
        return Result.success();
    }


}
