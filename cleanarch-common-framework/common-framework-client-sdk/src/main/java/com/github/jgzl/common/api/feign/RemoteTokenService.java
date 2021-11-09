package com.github.jgzl.common.api.feign;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.jgzl.common.core.constant.SecurityConstants;
import com.github.jgzl.common.core.constant.ServiceNameConstants;
import com.github.jgzl.common.core.util.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import java.util.Map;

/**
 * @author lihaifeng
 * @date 2020/9/4
 */
@FeignClient(contextId = "remoteTokenService", value = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteTokenService {

	/**
	 * 分页查询token 信息
	 * @param from 内部调用标志
	 * @param params 分页参数
	 * @param from 内部调用标志
	 * @return page
	 */
	@GetMapping("/token/page")
	Result<Page> getTokenPage(@SpringQueryMap Map<String, Object> params,
							  @RequestHeader(SecurityConstants.FROM) String from);

	/**
	 * 删除token
	 * @param from 内部调用标志
	 * @param token token
	 * @param from 内部调用标志
	 * @return
	 */
	@DeleteMapping("/token/{token}")
	Result<Boolean> removeTokenById(@PathVariable("token") String token, @RequestHeader(SecurityConstants.FROM) String from);

}
