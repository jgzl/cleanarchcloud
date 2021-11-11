package com.github.jgzl.infra.upms.service;

import com.github.jgzl.common.data.mybatis.SuperService;
import com.github.jgzl.infra.upms.domain.entity.message.StationMessagePublish;
import com.github.jgzl.infra.upms.domain.enums.ReceiverType;
import com.github.jgzl.infra.upms.domain.vo.CommonDataResp;

import java.util.List;

/**
 * @author levin
 */
public interface StationMessagePublishService extends SuperService<StationMessagePublish> {

    /**
     * 根据类型和条件查询
     *
     * @param type   类型
     * @param search 条件
     * @return 查询结果
     */
    List<CommonDataResp> queryReceiverByType(ReceiverType type, String search);

    /**
     * 发布消息
     *
     * @param id id
     */
    void publish(Long id);
}
