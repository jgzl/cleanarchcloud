package com.github.jgzl.application.auth.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;

import com.github.jgzl.application.auth.convert.SysUserConvert;
import com.github.jgzl.application.auth.mapper.SysUserMapper;
import com.github.jgzl.application.auth.service.SysUserService;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.core.constant.CommonConstants;
import com.github.jgzl.common.data.redis.CustomRedisRepository;
import com.github.jgzl.common.security.dataobject.SysUserDO;
import com.github.jgzl.common.security.vo.Operation;
import com.github.jgzl.common.security.vo.SysRoleVO;
import com.github.jgzl.common.security.vo.SysUserVO;
import com.github.jgzl.common.security.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserDO> implements SysUserService {

	@Autowired
	private CustomRedisRepository redisRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public UserVO findUserByUsername(String username) {
		SysUserDO dao = this.baseMapper
				.selectOne(Wrappers.<SysUserDO>lambdaQuery().eq(SysUserDO::getUsername, username));
		UserVO user = SysUserConvert.INSTANCE.convertUserDetails(dao);
		user.setEnabled(true);
		user.setExpired(false);
		user.setLocked(false);
		user.setPasswordExpired(false);
		user.setRoles(Collections.singletonList(defaultRole()));
		return user;
	}

	@Override
	public UserVO findUserByMobile(String mobile) {
		SysUserDO dao = this.baseMapper
				.selectOne(Wrappers.<SysUserDO>lambdaQuery().eq(SysUserDO::getMobile, mobile));
		UserVO user = SysUserConvert.INSTANCE.convertUserDetails(dao);
		user.setEnabled(true);
		user.setExpired(false);
		user.setLocked(false);
		user.setPasswordExpired(false);
		user.setRoles(Collections.singletonList(defaultRole()));
		return user;
	}

	@Override
	public SysUserVO getVo(final String id) {
		final SysUserDO userDO = this.getById(id);
		SysUserVO userVO = SysUserConvert.INSTANCE.convert(userDO);
		return userVO;
	}

	@Override
	public Boolean update(final SysUserVO vo) {
		final String key = CacheConstants.REDIS_USER_PREFIX + vo.getUserId();
		if (redisRepository.exists(key)) {
			if (log.isDebugEnabled()) {
				log.debug("Remove client:{} from redis.", key);
			}
			redisRepository.del(key);
		}
		if (vo.getEmail() != null && vo.getEmail().contains("***")) {
			vo.setEmail(null);
		}
		if (vo.getMobile() != null && vo.getMobile().contains("***")) {
			vo.setMobile(null);
		}
		SysUserDO userDO = SysUserConvert.INSTANCE.convert(vo);
		return this.updateById(userDO);
	}

	@Override
	public Boolean add(final SysUserVO vo) {
		SysUserDO userDO = SysUserConvert.INSTANCE.convert(vo);
		//密码进行加密
		userDO.setPassword(encoder.encode(userDO.getPassword()));
		userDO.setLoginCount(0);
		userDO.setLoginErrorCount(0);
		userDO.setLoginTime(LocalDateTime.now());
		return this.save(userDO);
	}

	@Override
	public Boolean delete(final String id) {
		final String key = CacheConstants.REDIS_USER_PREFIX + id;
		if (redisRepository.exists(key)) {
			if (log.isDebugEnabled()) {
				log.debug("Remove client:{} from redis.", key);
			}
			redisRepository.del(key);
		}
		return this.removeById(id);
	}

	@Override
	public IPage<SysUserVO> selectPageVo(final Page page) {
		final IPage<SysUserDO> iPage = this.page(page);
		return iPage.convert(SysUserVO::new);
	}

	private SysRoleVO defaultRole() {
		return new SysRoleVO().setRemark(
				CommonConstants.ROLE_DEFAULT)
				.setOperations(Collections.singletonList(new Operation(CommonConstants.OP_DEFAULT)));
	}
}
