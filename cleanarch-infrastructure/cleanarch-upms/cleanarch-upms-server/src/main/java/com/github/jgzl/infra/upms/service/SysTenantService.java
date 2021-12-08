package com.github.jgzl.infra.upms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.jgzl.infra.upms.api.entity.SysTenant;

import java.util.List;

/**
 * 租户管理
 *
 * @author lihaifeng
 * @date 2019-05-15 15:55:41
 */
public interface SysTenantService extends IService<SysTenant> {

	/**
	 * 获取正常的租户
	 * @return
	 */
	List<SysTenant> getNormalTenant();

	/**
	 * 保存租户
	 * @param sysTenant
	 * @return
	 */
	Boolean saveTenant(SysTenant sysTenant);

}
