package com.github.jgzl.infra.upms.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.jgzl.infra.upms.api.dto.UserInfo;
import com.github.jgzl.infra.upms.api.entity.SysSocialDetails;
import com.github.jgzl.infra.upms.api.entity.SysUser;
import com.github.jgzl.infra.upms.mapper.SysSocialDetailsMapper;
import com.github.jgzl.infra.upms.service.SysUserService;
import com.github.jgzl.common.core.constant.SecurityConstants;
import com.github.jgzl.common.core.constant.enums.LoginTypeEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lihaifeng
 * @date 2019/4/8
 * <p>
 * 开源中国登录
 */
@Slf4j
@Component("OSC")
@AllArgsConstructor
public class OscChinaLoginHandler extends AbstractLoginHandler {

	private final SysSocialDetailsMapper sysSocialDetailsMapper;

	private final SysUserService sysUserService;

	/**
	 * 开源中国传入code
	 * <p>
	 * 通过code 调用qq 获取唯一标识
	 * @param code
	 * @return
	 */
	@Override
	public String identify(String code) {
		SysSocialDetails condition = new SysSocialDetails();
		condition.setType(LoginTypeEnum.OSC.getType());
		SysSocialDetails socialDetails = sysSocialDetailsMapper.selectOne(new QueryWrapper<>(condition));

		Map<String, Object> params = new HashMap<>(8);

		params.put("client_id", socialDetails.getAppId());
		params.put("client_secret", socialDetails.getAppSecret());
		params.put("grant_type", "authorization_code");
		params.put("redirect_uri", socialDetails.getRedirectUrl());
		params.put("code", code);
		params.put("dataType", "json");

		String result = HttpUtil.post(SecurityConstants.OSC_AUTHORIZATION_CODE_URL, params);
		log.debug("开源中国响应报文:{}", result);

		String accessToken = JSONUtil.parseObj(result).getStr("access_token");

		String url = String.format(SecurityConstants.OSC_USER_INFO_URL, accessToken);
		String resp = HttpUtil.get(url);
		log.debug("开源中国获取个人信息返回报文{}", resp);

		JSONObject userInfo = JSONUtil.parseObj(resp);
		// 开源中国唯一标识
		String id = userInfo.getStr("id");
		return id;
	}

	/**
	 * identify 获取用户信息
	 * @param identify 开源中国表示
	 * @return
	 */
	@Override
	public UserInfo info(String identify) {

		SysUser user = sysUserService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getOscId, identify));

		if (user == null) {
			log.info("开源中国未绑定:{}", identify);
			return null;
		}
		return sysUserService.findUserInfo(user);
	}

	/**
	 * 绑定逻辑
	 * @param user 用户实体
	 * @param identify 渠道返回唯一标识
	 * @return
	 */
	@Override
	public Boolean bind(SysUser user, String identify) {
		List<SysUser> userList = sysUserService
				.list(Wrappers.<SysUser>query().lambda().eq(SysUser::getOscId, identify));

		// 先把原有绑定关系去除,设置绑定为NULL
		if (CollUtil.isNotEmpty(userList)) {
			SysUser condition = new SysUser();
			condition.setOscId(identify);
			sysUserService.update(condition, Wrappers.<SysUser>lambdaUpdate().set(SysUser::getOscId, null));
			log.info("开源中国账号 {} 更换账号绑定", identify);
		}

		user.setOscId(identify);
		sysUserService.updateById(user);
		return null;
	}

}
