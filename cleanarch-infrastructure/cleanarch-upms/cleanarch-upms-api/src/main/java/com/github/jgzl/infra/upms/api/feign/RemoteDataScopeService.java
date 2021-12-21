package com.github.jgzl.infra.upms.api.feign;

import com.github.jgzl.common.core.constant.ServiceNameConstants;
import com.github.jgzl.common.core.util.R;
import com.github.jgzl.infra.upms.api.entity.SysDeptRelation;
import com.github.jgzl.infra.upms.api.entity.SysRole;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author lihaifeng
 * @date 2019-09-07
 * <p>
 * 远程数据权限调用接口
 */
@FeignClient(contextId = "remoteDataScopeService", value = ServiceNameConstants.UPMS_SERVICE)
public interface RemoteDataScopeService {

	/**
	 * 通过角色ID 查询角色列表
	 *
	 * @param roleIdList 角色ID
	 * @return
	 */
	@PostMapping("/role/getRoleList")
	R<List<SysRole>> getRoleList(@RequestBody List<String> roleIdList);

	/**
	 * 获取子级部门
	 *
	 * @param deptId 部门ID
	 * @return
	 */
	@GetMapping("/dept/getDescendantList/{deptId}")
	R<List<SysDeptRelation>> getDescendantList(@PathVariable("deptId") Integer deptId);

}
