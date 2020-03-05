package com.alibaba.csp.sentinel.dashboard.rule.nacos.systemrule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.SystemRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.CustomNacosConfigUtil;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.nacos.api.config.ConfigService;

/**
 * @vlog: 高于生活，源于生活
 * @desc: 类的描述:系统规则提供类
 * @author: smlz
 * @createDate: 2019/12/2 19:17
 * @version: 1.0
 */
@Component("systemRuleNacosProvider")
public class SystemRuleNacosProvider implements DynamicRuleProvider<List<SystemRuleEntity>> {

	@Autowired
	private ConfigService configService;

	@Autowired
	private Converter<String, List<SystemRuleEntity>> converter;

	@Override
	public List<SystemRuleEntity> getRules(String appName) throws Exception {
		return CustomNacosConfigUtil.getRuleEntities4Nacos(
				configService,
				appName,
				CustomNacosConfigUtil.SYSTEM_FULE_DATA_ID_POSTFIX,
				SystemRuleEntity.class,
				converter
		);
	}
}
