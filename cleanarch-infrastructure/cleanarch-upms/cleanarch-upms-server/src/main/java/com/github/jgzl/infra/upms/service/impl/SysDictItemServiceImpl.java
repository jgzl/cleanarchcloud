package com.github.jgzl.infra.upms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.core.constant.enums.DictTypeEnum;
import com.github.jgzl.common.core.util.R;
import com.github.jgzl.infra.upms.api.entity.SysDict;
import com.github.jgzl.infra.upms.api.entity.SysDictItem;
import com.github.jgzl.infra.upms.mapper.SysDictItemMapper;
import com.github.jgzl.infra.upms.service.SysDictItemService;
import com.github.jgzl.infra.upms.service.SysDictService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

/**
 * 字典项
 *
 * @author lihaifeng
 * @date 2019/03/19
 */
@Service
@AllArgsConstructor
public class SysDictItemServiceImpl extends ServiceImpl<SysDictItemMapper, SysDictItem> implements SysDictItemService {

	private final SysDictService dictService;

	/**
	 * 删除字典项
	 *
	 * @param id 字典项ID
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
	public R removeDictItem(Integer id) {
		// 根据ID查询字典ID
		SysDictItem dictItem = this.getById(id);
		SysDict dict = dictService.getById(dictItem.getDictId());
		// 系统内置
		if (DictTypeEnum.SYSTEM.getType().equals(dict.getSystem())) {
			return R.failed("系统内置字典项目不能删除");
		}
		return R.ok(this.removeById(id));
	}

	/**
	 * 更新字典项
	 *
	 * @param item 字典项
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
	public R updateDictItem(SysDictItem item) {
		// 查询字典
		SysDict dict = dictService.getById(item.getDictId());
		// 系统内置
		if (DictTypeEnum.SYSTEM.getType().equals(dict.getSystem())) {
			return R.failed("系统内置字典项目不能删除");
		}
		return R.ok(this.updateById(item));
	}

}
