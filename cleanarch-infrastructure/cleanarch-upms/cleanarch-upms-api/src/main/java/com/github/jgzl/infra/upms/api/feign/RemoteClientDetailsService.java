package com.github.jgzl.infra.upms.api.feign;

import com.github.jgzl.common.core.constant.SecurityConstants;
import com.github.jgzl.common.core.constant.ServiceNameConstants;
import com.github.jgzl.common.core.util.R;
import com.github.jgzl.infra.upms.api.entity.SysOauthClientDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * @author lihaifeng
 * @date 2020/12/05
 */
@FeignClient(contextId = "remoteClientDetailsService", value = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteClientDetailsService {

	/**
	 * 通过clientId 查询客户端信息
	 *
	 * @param clientId 用户名
	 * @param from     调用标志
	 * @return R
	 */
	@GetMapping("/client/getClientDetailsById/{clientId}")
	R<SysOauthClientDetails> getClientDetailsById(@PathVariable("clientId") String clientId,
												  @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 查询全部客户端
	 *
	 * @param from 调用标识
	 * @return R
	 */
	@GetMapping("/client/list")
	R<List<SysOauthClientDetails>> listClientDetails(@RequestHeader(SecurityConstants.FROM) String from);

}
