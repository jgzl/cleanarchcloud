package com.github.jgzl.common.data.tenant;
import cn.hutool.core.util.StrUtil;
import com.github.jgzl.common.core.constant.CommonConstants;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author lihaifeng
 * @date 2020/9/13
 */
@Slf4j
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TenantContextHolderFilter extends GenericFilterBean {
	@Override
	@SneakyThrows
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String headerTenant = request.getHeader(CommonConstants.TENANT_CODE);
		String paramTenant = request.getParameter(CommonConstants.TENANT_CODE);
		log.debug("获取header中的租户ID为:{}", headerTenant);
		if (StrUtil.isNotBlank(headerTenant)) {
			TenantContextHolder.setTenantCode(headerTenant);
		}
		else if (StrUtil.isNotBlank(paramTenant)) {
			TenantContextHolder.setTenantCode(paramTenant);
		}
		else {
			TenantContextHolder.setTenantCode(CommonConstants.DEFAULT_TENANT_CODE);
		}
		filterChain.doFilter(request, response);
		TenantContextHolder.clear();
	}
}
