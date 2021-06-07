package com.github.jgzl.application.auth.service;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jgzl.application.auth.dataobject.SysRouteConf;
import reactor.core.publisher.Mono;

/**
 * 路由
 *
 * @author lengleng
 * @date 2018-11-06 10:17:18
 */
public interface SysRouteConfService extends IService<SysRouteConf> {

	/**
	 * 更新路由信息
	 * @param routes 路由信息
	 * @return
	 */
	Mono<Void> updateRoutes(JSONArray routes);

}
