package com.alibaba.csp.sentinel.dashboard.rule.nacos.hotparamrule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRuleProvider;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.CustomNacosConfigUtil;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.nacos.api.config.ConfigService;

/**
 * @vlog: 高于生活，源于生活
 * @desc: 类的描述:热点参数限流规则类
 * @author: smlz
 * @createDate: 2019/12/2 15:22
 * @version: 1.0
 */
@Component("paramFlowRuleNacosProvider")
public class ParamFlowRuleNacosProvider implements DynamicRuleProvider<List<ParamFlowRuleEntity>> {

	@Autowired
	private ConfigService configService;

	@Autowired
	private Converter<String, List<ParamFlowRuleEntity>> converter;

	@Override
	public List<ParamFlowRuleEntity> getRules(String appName) throws Exception {
		return CustomNacosConfigUtil.getRuleEntities4Nacos(
				configService,
				appName,
				CustomNacosConfigUtil.PARAM_FLOW_DATA_ID_POSTFIX,
				ParamFlowRuleEntity.class,
				converter
		);
	}
}
