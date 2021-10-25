package com.github.jgzl.common.api.feign;
import com.github.jgzl.common.core.constant.ServiceNameConstants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Map;

/**
 * @author lihaifeng
 * @date 2018/6/28
 */
@FeignClient(contextId = "remoteOauth2Service", value = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteOauth2Service {

	/**
	 * 查询public key
	 * @return xxxx
	 */
	@GetMapping("/oauth/token_key")
	Map<String,String> token_key();

}
