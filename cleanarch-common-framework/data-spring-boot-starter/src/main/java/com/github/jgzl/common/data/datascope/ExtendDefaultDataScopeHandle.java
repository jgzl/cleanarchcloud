package com.github.jgzl.common.data.datascope;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.github.jgzl.common.core.constant.SecurityConstants;
import com.github.jgzl.common.security.service.ExtendUser;
import com.github.jgzl.common.security.util.SecurityUtils;
import com.github.jgzl.infra.upms.api.entity.SysDeptRelation;
import com.github.jgzl.infra.upms.api.entity.SysRole;
import com.github.jgzl.infra.upms.api.feign.RemoteDataScopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lihaifeng
 * @date 2019-09-07
 * <p>
 * 默认data scope 判断处理器
 */
public class ExtendDefaultDataScopeHandle implements DataScopeHandle {

	@Autowired
	private RemoteDataScopeService dataScopeService;

	/**
	 * 计算用户数据权限
	 *
	 * @param deptList
	 * @return
	 */
	@Override
	public Boolean calcScope(List<Integer> deptList) {
		ExtendUser user = SecurityUtils.getUser();
		List<String> roleIdList = user.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.filter(authority -> authority.startsWith(SecurityConstants.ROLE))
				.map(authority -> authority.split(StrUtil.UNDERLINE)[1]).collect(Collectors.toList());
		// 当前用户的角色为空
		if (CollectionUtil.isEmpty(roleIdList)) {
			return false;
		}
		SysRole role = dataScopeService.getRoleList(roleIdList).getData().stream()
				.min(Comparator.comparingInt(SysRole::getDsType)).orElse(null);
		// 角色有可能已经删除了
		if (role == null) {
			return false;
		}
		Integer dsType = role.getDsType();
		// 查询全部
		if (DataScopeTypeEnum.ALL.getType() == dsType) {
			return true;
		}
		// 自定义
		if (DataScopeTypeEnum.CUSTOM.getType() == dsType && StrUtil.isNotBlank(role.getDsScope())) {
			String dsScope = role.getDsScope();
			deptList.addAll(
					Arrays.stream(dsScope.split(StrUtil.COMMA)).map(Integer::parseInt).collect(Collectors.toList()));
		}
		// 查询本级及其下级
		if (DataScopeTypeEnum.OWN_CHILD_LEVEL.getType() == dsType) {
			List<Integer> deptIdList = dataScopeService.getDescendantList(user.getDeptId()).getData().stream()
					.map(SysDeptRelation::getDescendant).collect(Collectors.toList());
			deptList.addAll(deptIdList);
		}
		// 只查询本级
		if (DataScopeTypeEnum.OWN_LEVEL.getType() == dsType) {
			deptList.add(user.getDeptId());
		}
		return false;
	}

}
