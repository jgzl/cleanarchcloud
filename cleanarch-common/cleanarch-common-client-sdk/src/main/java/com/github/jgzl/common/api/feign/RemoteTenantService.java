package com.github.jgzl.common.api.feign;

import com.github.jgzl.common.api.dataobject.SysTenant;
import com.github.jgzl.common.core.constant.SecurityConstants;
import com.github.jgzl.common.core.constant.ServiceNameConstants;
import com.github.jgzl.common.core.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @author lihaifeng
 * @date 2019/6/19
 * <p>
 * 租户接口
 */
@FeignClient(contextId = "remoteTenantService", value = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteTenantService {

	/**
	 * 查询全部有效租户
	 * @param from 内部标志
	 * @return
	 */
	@GetMapping("/tenant/list")
	Result<List<SysTenant>> list(@RequestHeader(SecurityConstants.FROM) String from);

}
