package com.github.jgzl.infra.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jgzl.infra.upms.api.entity.SysDict;
import com.github.jgzl.common.core.util.R;

/**
 * 字典表
 *
 * @author lihaifeng
 * @date 2019/03/19
 */
public interface SysDictService extends IService<SysDict> {

	/**
	 * 根据ID 删除字典
	 * @param id
	 * @return
	 */
	R removeDict(Integer id);

	/**
	 * 更新字典
	 * @param sysDict 字典
	 * @return
	 */
	R updateDict(SysDict sysDict);

	/**
	 * 同步缓存 （清空缓存）
	 * @return R
	 */
	R syncDictCache();

}
