package com.github.jgzl.infra.upms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jgzl.infra.upms.api.dto.SysLogDTO;
import com.github.jgzl.infra.upms.api.entity.SysLog;
import com.github.jgzl.infra.upms.api.vo.PreLogVO;

import java.util.List;

/**
 * <p>
 * 日志表 服务类
 * </p>
 *
 * @author lihaifeng
 * @since 2017-11-20
 */
public interface SysLogService extends IService<SysLog> {

	/**
	 * 批量插入前端错误日志
	 *
	 * @param preLogVoList 日志信息
	 * @return true/false
	 */
	Boolean saveBatchLogs(List<PreLogVO> preLogVoList);

	/**
	 * 分页查询日志
	 *
	 * @param page
	 * @param sysLog
	 * @return
	 */
	Page getLogByPage(Page page, SysLogDTO sysLog);

	/**
	 * 插入日志
	 *
	 * @param sysLog 日志对象
	 * @return true/false
	 */
	Boolean saveLog(SysLogDTO sysLog);

}
