package com.github.jgzl.infra.upms.service;
import com.github.jgzl.common.api.vo.OptLogDTO;
import com.github.jgzl.common.data.mybatis.SuperService;
import com.github.jgzl.infra.upms.domain.entity.log.OptLog;
/**
 * @author lihaifeng
 */
public interface OptLogService extends SuperService<OptLog> {
    /**
     * 保存操作日志
     *
     * @param dto dto
     */
    void save(OptLogDTO dto);
}
