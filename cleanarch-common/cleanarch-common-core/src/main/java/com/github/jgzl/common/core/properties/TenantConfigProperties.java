package com.github.jgzl.common.core.properties;
import com.github.jgzl.common.core.constant.CommonConstants;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;
import java.util.ArrayList;
import java.util.List;

/**
 * 多租户配置
 *
 * @author oathsign
 */
@Data
@RefreshScope
@Configuration
@ConfigurationProperties(prefix = CommonConstants.SYS_NAME+".tenant")
public class TenantConfigProperties {

	/**
	 * 维护租户列名称
	 */
	private String column = "tenant_id";

	/**
	 * 多租户的数据表集合
	 */
	private List<String> tables = new ArrayList<>();

}
