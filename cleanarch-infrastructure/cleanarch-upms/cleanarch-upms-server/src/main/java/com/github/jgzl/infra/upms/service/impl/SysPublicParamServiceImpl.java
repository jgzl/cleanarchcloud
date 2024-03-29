package com.github.jgzl.infra.upms.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.core.constant.enums.DictTypeEnum;
import com.github.jgzl.common.core.util.R;
import com.github.jgzl.infra.upms.api.entity.SysPublicParam;
import com.github.jgzl.infra.upms.mapper.SysPublicParamMapper;
import com.github.jgzl.infra.upms.service.SysPublicParamService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 公共参数配置
 *
 * @author Lucky
 * @date 2019-04-29
 */
@Service
@AllArgsConstructor
public class SysPublicParamServiceImpl extends ServiceImpl<SysPublicParamMapper, SysPublicParam>
		implements SysPublicParamService {

	@Override
	@Cacheable(value = CacheConstants.PARAMS_DETAILS, key = "#publicKey", unless = "#result == null ")
	public String getSysPublicParamKeyToValue(String publicKey) {
		SysPublicParam sysPublicParam = this.baseMapper
				.selectOne(Wrappers.<SysPublicParam>lambdaQuery().eq(SysPublicParam::getPublicKey, publicKey));

		if (sysPublicParam != null) {
			return sysPublicParam.getPublicValue();
		}
		return null;
	}

	/**
	 * 更新参数
	 *
	 * @param sysPublicParam
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.PARAMS_DETAILS, key = "#sysPublicParam.publicKey")
	public R updateParam(SysPublicParam sysPublicParam) {
		SysPublicParam param = this.getById(sysPublicParam.getPublicId());
		// 系统内置
		if (DictTypeEnum.SYSTEM.getType().equals(param.getSystem())) {
			return R.failed("系统内置参数不能删除");
		}
		return R.ok(this.updateById(sysPublicParam));
	}

	/**
	 * 删除参数
	 *
	 * @param publicId
	 * @return
	 */
	@Override
	@CacheEvict(value = CacheConstants.PARAMS_DETAILS, allEntries = true)
	public R removeParam(Long publicId) {
		SysPublicParam param = this.getById(publicId);
		// 系统内置
		if (DictTypeEnum.SYSTEM.getType().equals(param.getSystem())) {
			return R.failed("系统内置参数不能删除");
		}
		return R.ok(this.removeById(publicId));
	}

	/**
	 * 同步缓存
	 *
	 * @return R
	 */
	@Override
	@CacheEvict(value = CacheConstants.PARAMS_DETAILS, allEntries = true)
	public R syncDictCache() {
		return R.ok();
	}

}
