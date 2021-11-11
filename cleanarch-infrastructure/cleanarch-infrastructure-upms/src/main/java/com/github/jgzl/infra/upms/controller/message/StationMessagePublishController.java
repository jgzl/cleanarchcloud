package com.github.jgzl.infra.upms.controller.message;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.common.core.annotation.log.SysLog;
import com.github.jgzl.common.core.util.Result;
import com.github.jgzl.common.core.util.StringUtils;
import com.github.jgzl.common.data.configuration.dynamic.ann.DynamicDS;
import com.github.jgzl.common.data.mybatis.conditions.Wraps;
import com.github.jgzl.common.data.page.PageRequest;
import com.github.jgzl.infra.upms.domain.dto.StationMessagePublishReq;
import com.github.jgzl.infra.upms.domain.entity.message.StationMessagePublish;
import com.github.jgzl.infra.upms.domain.enums.ReceiverType;
import com.github.jgzl.infra.upms.domain.vo.CommonDataResp;
import com.github.jgzl.infra.upms.domain.vo.StationMessagePublishResp;
import com.github.jgzl.infra.upms.service.StationMessagePublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.github.jgzl.common.core.util.Result.success;
import static com.github.jgzl.infra.upms.domain.converts.StationMessagePublishConverts.STATION_MESSAGE_PUBLISH_2_VO_CONVERTS;

/**
 * @author Levin
 */
@DynamicDS
@RequestMapping("station_messages_publish")
@RestController
public class StationMessagePublishController {

	@Autowired
    private StationMessagePublishService stationMessagePublishService;

    @GetMapping
    public Result<IPage<StationMessagePublishResp>> publishList(PageRequest request, String title, Integer level, Integer type) {
        final Page<StationMessagePublish> page = stationMessagePublishService.page(request.buildPage(), Wraps.<StationMessagePublish>lbQ()
                .eq(StationMessagePublish::getTitle, title).eq(StationMessagePublish::getLevel, level)
                .eq(StationMessagePublish::getType, ReceiverType.of(type)));
        return success(STATION_MESSAGE_PUBLISH_2_VO_CONVERTS.convertPage(page));
    }


    @GetMapping("/{type}/receivers")
    public Result<List<CommonDataResp>> query(@PathVariable ReceiverType type, String search) {
        return success(this.stationMessagePublishService.queryReceiverByType(type, search));
    }

    @PostMapping
    @SysLog(value = "添加发布消息")
    public Result<ResponseEntity<Void>> add(@Validated @RequestBody StationMessagePublishReq req) {
        final StationMessagePublish bean = BeanUtil.toBean(req, StationMessagePublish.class);
        bean.setReceiver(StringUtils.join(",",req.getReceiver()));
        stationMessagePublishService.save(bean);
        return success();
    }

    @PutMapping("/{id}")
    @SysLog(value = "编辑发布消息")
    public Result<ResponseEntity<Void>> edit(@PathVariable Long id, @Validated @RequestBody StationMessagePublishReq req) {
        final StationMessagePublish bean = BeanUtil.toBean(req, StationMessagePublish.class);
        bean.setReceiver(StringUtils.join(",",req.getReceiver()));
        bean.setId(id);
        stationMessagePublishService.updateById(bean);
        return success();
    }

    @DeleteMapping("/{id}")
    @SysLog(value = "删除发布消息")
    public Result<ResponseEntity<Void>> del(@PathVariable Long id) {
        stationMessagePublishService.removeById(id);
        return success();
    }

    @PatchMapping("/{id}/publish")
    @SysLog(value = "发布消息通知")
    public Result<ResponseEntity<Void>> publish(@PathVariable Long id) {
        stationMessagePublishService.publish(id);
        return success();
    }


}
