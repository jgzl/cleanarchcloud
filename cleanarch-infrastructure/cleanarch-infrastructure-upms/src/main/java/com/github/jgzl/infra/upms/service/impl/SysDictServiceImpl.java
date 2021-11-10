package com.github.jgzl.infra.upms.service.impl;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jgzl.infra.upms.dataobject.SysDict;
import com.github.jgzl.infra.upms.dataobject.SysDictItem;
import com.github.jgzl.infra.upms.mapper.SysDictItemMapper;
import com.github.jgzl.infra.upms.mapper.SysDictMapper;
import com.github.jgzl.infra.upms.service.SysDictService;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.core.constant.enums.DictTypeEnum;
import com.github.jgzl.common.core.util.Result;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 字典表
 *
 * @author lihaifeng
 * @date 2020/03/19
 */
@Service
@AllArgsConstructor
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

	private final SysDictItemMapper dictItemMapper;

	/**
	 * 根据ID 删除字典
	 * @param id 字典ID
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.DICT_DETAILS, allEntries = true)
	@Transactional(rollbackFor = Exception.class)
	public Result removeDict(Integer id) {
		SysDict dict = this.getById(id);
		// 系统内置
		if (DictTypeEnum.SYSTEM.getType().equals(dict.getSystem())) {
			return Result.fail("系统内置字典不能删除");
		}

		baseMapper.deleteById(id);
		dictItemMapper.delete(Wrappers.<SysDictItem>lambdaQuery().eq(SysDictItem::getDictId, id));
		return Result.success();
	}

	/**
	 * 更新字典
	 * @param dict 字典
	 * @return
	 */
	@Override
	public Result updateDict(SysDict dict) {
		SysDict sysDict = this.getById(dict.getId());
		// 系统内置
		if (DictTypeEnum.SYSTEM.getType().equals(sysDict.getSystem())) {
			return Result.fail("系统内置字典不能修改");
		}
		return Result.ok(this.updateById(dict));
	}

}
