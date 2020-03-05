package com.alibaba.csp.sentinel.dashboard.rule.nacos.hotparamrule;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.ParamFlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.rule.DynamicRulePublisher;
import com.alibaba.csp.sentinel.dashboard.rule.nacos.CustomNacosConfigUtil;
import com.alibaba.csp.sentinel.datasource.Converter;
import com.alibaba.nacos.api.config.ConfigService;

/**
 * @vlog: 高于生活，源于生活
 * @desc: 类的描述:热点参数限流规则发布类
 * @author: smlz
 * @createDate: 2019/12/2 15:22
 * @version: 1.0
 */
@Component("paramFlowRuleNacosPublisher")
public class ParamFlowRuleNacosPublisher implements DynamicRulePublisher<List<ParamFlowRuleEntity>> {

	@Autowired
	private ConfigService configService;

	@Autowired
	private Converter<List<ParamFlowRuleEntity>, String> converter;

	@Override
	public void publish(String app, List<ParamFlowRuleEntity> rules) throws Exception {
		CustomNacosConfigUtil.setRuleString2Nacos(
				configService,
				app,
				CustomNacosConfigUtil.PARAM_FLOW_DATA_ID_POSTFIX,
				rules,
				converter
		);
	}
}
