package com.github.jgzl.infra.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jgzl.common.core.util.R;
import com.github.jgzl.infra.upms.api.entity.SysDictItem;

/**
 * 字典项
 *
 * @author lihaifeng
 * @date 2019/03/19
 */
public interface SysDictItemService extends IService<SysDictItem> {

	/**
	 * 删除字典项
	 *
	 * @param id 字典项ID
	 * @return
	 */
	R removeDictItem(Integer id);

	/**
	 * 更新字典项
	 *
	 * @param item 字典项
	 * @return
	 */
	R updateDictItem(SysDictItem item);

}
