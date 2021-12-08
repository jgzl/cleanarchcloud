package com.github.jgzl.infra.upms.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpUtil;
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

import java.util.List;

/**
 * @author lihaifeng
 * @date 2019年11月02日
 * <p>
 * 微信小程序
 */
@Slf4j
@Component("MINI")
@AllArgsConstructor
public class MiniAppLoginHandler extends AbstractLoginHandler {

	private final SysUserService sysUserService;

	private final SysSocialDetailsMapper sysSocialDetailsMapper;

	/**
	 * 小程序登录传入code
	 * <p>
	 * 通过code 调用qq 获取唯一标识
	 * @param code
	 * @return
	 */
	@Override
	public String identify(String code) {
		SysSocialDetails condition = new SysSocialDetails();
		condition.setType(LoginTypeEnum.MINI_APP.getType());
		SysSocialDetails socialDetails = sysSocialDetailsMapper.selectOne(new QueryWrapper<>(condition));

		String url = String.format(SecurityConstants.MINI_APP_AUTHORIZATION_CODE_URL, socialDetails.getAppId(),
				socialDetails.getAppSecret(), code);
		String result = HttpUtil.get(url);
		log.debug("微信小程序响应报文:{}", result);

		Object obj = JSONUtil.parseObj(result).get("openid");
		return obj.toString();
	}

	/**
	 * openId 获取用户信息
	 * @param openId
	 * @return
	 */
	@Override
	public UserInfo info(String openId) {
		SysUser user = sysUserService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getMiniOpenid, openId));

		if (user == null) {
			log.info("微信小程序未绑定:{}", openId);
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
				.list(Wrappers.<SysUser>query().lambda().eq(SysUser::getMiniOpenid, identify));

		// 先把原有绑定关系去除,设置绑定为NULL
		if (CollUtil.isNotEmpty(userList)) {
			SysUser condition = new SysUser();
			condition.setMiniOpenid(identify);
			sysUserService.update(condition, Wrappers.<SysUser>lambdaUpdate().set(SysUser::getMiniOpenid, null));
			log.info("小程序账号 {} 更换账号绑定", identify);
		}

		user.setMiniOpenid(identify);
		sysUserService.updateById(user);
		return null;
	}

}
