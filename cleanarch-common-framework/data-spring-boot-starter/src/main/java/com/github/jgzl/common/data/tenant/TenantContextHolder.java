package com.github.jgzl.common.data.tenant;
import com.alibaba.ttl.TransmittableThreadLocal;
import lombok.experimental.UtilityClass;

/**
 * @author lihaifeng
 * @date 2020/10/4 租户工具类
 */
@UtilityClass
public class TenantContextHolder {

	private final ThreadLocal<String> THREAD_LOCAL_TENANT = new TransmittableThreadLocal<>();

	/**
	 * TTL 设置租户ID<br/>
	 * <b>谨慎使用此方法,避免嵌套调用。尽量使用 {@code TenantBroker} </b>
	 * @param tenantCode
	 * @see TenantBroker
	 */
	public void setTenantCode(String tenantCode) {
		THREAD_LOCAL_TENANT.set(tenantCode);
	}

	/**
	 * 获取TTL中的租户ID
	 * @return
	 */
	public String getTenantCode() {
		return THREAD_LOCAL_TENANT.get();
	}

	public void clear() {
		THREAD_LOCAL_TENANT.remove();
	}

}
