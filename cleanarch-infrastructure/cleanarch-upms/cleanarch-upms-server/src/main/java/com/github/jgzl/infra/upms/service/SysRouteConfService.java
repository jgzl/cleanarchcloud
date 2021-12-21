package com.github.jgzl.infra.upms.service;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jgzl.infra.upms.api.entity.SysRouteConf;
import reactor.core.publisher.Mono;

/**
 * 路由
 *
 * @author lihaifeng
 * @date 2018-11-06 10:17:18
 */
public interface SysRouteConfService extends IService<SysRouteConf> {

	/**
	 * 更新路由信息
	 *
	 * @param routes 路由信息
	 * @return
	 */
	Mono<Void> updateRoutes(JSONArray routes);

}
