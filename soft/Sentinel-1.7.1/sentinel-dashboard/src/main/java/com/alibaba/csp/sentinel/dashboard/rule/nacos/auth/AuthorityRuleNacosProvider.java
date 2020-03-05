package com.alibaba.csp.sentinel.dashboard.rule.nacos.auth;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.AuthorityRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.CustomNacosConfigUtil;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.nacos.api.config.ConfigService;

/**
 * @vlog: 高于生活，源于生活
 * @desc: 类的描述:基于Nacos配置中心持久化
 * @author: smlz
 * @createDate: 2019/12/2 14:31
 * @version: 1.0
 */
@Component("authorityRuleNacosProvider")
public class AuthorityRuleNacosProvider implements DynamicRuleProvider<List<AuthorityRuleEntity>> {

	@Autowired
	private ConfigService configService;

	@Autowired
	private Converter<String, List<AuthorityRuleEntity>> converter;

	@Override
	public List<AuthorityRuleEntity> getRules(String appName) throws Exception {
		return CustomNacosConfigUtil.getRuleEntities4Nacos(
				this.configService,
				appName,
				CustomNacosConfigUtil.AUTHORITY_DATA_ID_POSTFIX,
				AuthorityRuleEntity.class,
				converter
		);
	}
}
