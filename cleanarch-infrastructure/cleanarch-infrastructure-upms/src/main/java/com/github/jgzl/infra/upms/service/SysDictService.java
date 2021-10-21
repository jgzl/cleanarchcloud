package com.github.jgzl.infra.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jgzl.infra.upms.dataobject.SysDict;
import com.github.jgzl.common.core.util.Result;

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
	Result removeDict(Integer id);

	/**
	 * 更新字典
	 * @param sysDict 字典
	 * @return
	 */
	Result updateDict(SysDict sysDict);

}
