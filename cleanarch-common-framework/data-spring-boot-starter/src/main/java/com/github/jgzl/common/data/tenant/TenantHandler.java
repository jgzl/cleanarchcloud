package com.github.jgzl.common.data.tenant;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.handler.TenantLineHandler;
import com.github.jgzl.common.data.TenantEnvironment;
import com.github.jgzl.common.data.properties.FrameworkMpProperties;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.NullValue;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lihaifeng
 * @date 2018-12-26
 * 租户维护处理器
 */
@Slf4j
@AllArgsConstructor
public class TenantHandler implements TenantLineHandler {

	private FrameworkMpProperties.MultiTenant tenant;

	private TenantEnvironment tenantEnvironment;

	/**
	 * 获取租户 ID 值表达式，只支持单个 ID 值
	 * <p>
	 * @return 租户 ID 值表达式
	 */
	@Override
	public Expression getTenantId() {
		// 租户ID
		log.debug("当前租户ID - {}", tenantEnvironment.tenantId());
		return new LongValue(tenantEnvironment.tenantId());
	}

	/**
	 * 获取租户字段名
	 * @return 租户字段名
	 */
	@Override
	public String getTenantIdColumn() {
		return tenant.getTenantIdColumn();
	}

	/**
	 * 根据表名判断是否忽略拼接多租户条件
	 * <p>
	 * 默认都要进行解析并拼接多租户条件
	 * @param tableName 表名
	 * @return 是否忽略, true:表示忽略，false:需要解析并拼接多租户条件
	 */
	@Override
	public boolean ignoreTable(String tableName) {
		String tenantCode = TenantContextHolder.getTenantCode();
		// 租户中ID 为空，查询全部，不进行过滤
		if (StrUtil.isBlank(tenantCode)) {
			return Boolean.TRUE;
		}
		final List<String> tables = tenant.getIncludeTables();
		//  判断哪些表不需要多租户判断,返回false表示都需要进行多租户判断
		return tenantEnvironment.anonymous() || !tables.contains(tableName);
	}

}
