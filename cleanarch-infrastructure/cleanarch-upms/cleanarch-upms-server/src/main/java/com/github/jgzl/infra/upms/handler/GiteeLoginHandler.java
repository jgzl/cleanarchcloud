package com.github.jgzl.infra.upms.handler;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.URLUtil;
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

/**
 * @author lihaifeng
 * @date 2019/4/8
 * <p>
 * 码云登录
 */
@Slf4j
@Component("GITEE")
@AllArgsConstructor
public class GiteeLoginHandler extends AbstractLoginHandler {

	private final SysSocialDetailsMapper sysSocialDetailsMapper;

	private final SysUserService sysUserService;

	/**
	 * 码云登录传入code
	 * <p>
	 * 通过code 调用qq 获取唯一标识
	 * @param code
	 * @return
	 */
	@Override
	public String identify(String code) {
		SysSocialDetails condition = new SysSocialDetails();
		condition.setType(LoginTypeEnum.GITEE.getType());
		SysSocialDetails socialDetails = sysSocialDetailsMapper.selectOne(new QueryWrapper<>(condition));

		String url = String.format(SecurityConstants.GITEE_AUTHORIZATION_CODE_URL, code, socialDetails.getAppId(),
				URLUtil.encode(socialDetails.getRedirectUrl()), socialDetails.getAppSecret());
		String result = HttpUtil.post(url, new HashMap<>(0));
		log.debug("码云响应报文:{}", result);

		String accessToken = JSONUtil.parseObj(result).getStr("access_token");
		String userUrl = String.format(SecurityConstants.GITEE_USER_INFO_URL, accessToken);
		String resp = HttpUtil.get(userUrl);
		log.debug("码云获取个人信息返回报文{}", resp);

		JSONObject userInfo = JSONUtil.parseObj(resp);
		// 码云唯一标识
		String login = userInfo.getStr("login");
		return login;
	}

	/**
	 * identify 获取用户信息
	 * @param identify identify
	 * @return
	 */
	@Override
	public UserInfo info(String identify) {

		SysUser user = sysUserService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getGiteeLogin, identify));

		if (user == null) {
			log.info("码云未绑定:{}", identify);
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
				.list(Wrappers.<SysUser>query().lambda().eq(SysUser::getGiteeLogin, identify));

		// 先把原有绑定关系去除,设置绑定为NULL
		if (CollUtil.isNotEmpty(userList)) {
			SysUser condition = new SysUser();
			condition.setGiteeLogin(identify);
			sysUserService.update(condition, Wrappers.<SysUser>lambdaUpdate().set(SysUser::getGiteeLogin, null));
			log.info("码云账号 {} 更换账号绑定", identify);
		}

		user.setGiteeLogin(identify);
		sysUserService.updateById(user);
		return true;
	}

}
