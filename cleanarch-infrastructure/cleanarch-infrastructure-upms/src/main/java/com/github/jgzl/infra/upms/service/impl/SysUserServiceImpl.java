package com.github.jgzl.infra.upms.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jgzl.infra.upms.convert.SysUserConvert;
import com.github.jgzl.infra.upms.mapper.SysUserMapper;
import com.github.jgzl.infra.upms.service.SysUserService;
import com.github.jgzl.common.cache.support.CustomRedisRepository;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.core.constant.CommonConstants;
import com.github.jgzl.common.security.dataobject.SysUserDo;
import com.github.jgzl.common.security.vo.Operation;
import com.github.jgzl.common.security.vo.SysRoleVo;
import com.github.jgzl.common.security.vo.SysUserVo;
import com.github.jgzl.common.security.vo.UserVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;

/**
 * @author Administrator
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserDo> implements SysUserService {

	@Autowired
	private CustomRedisRepository redisRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public UserVo findUserByUsername(String username) {
		SysUserDo dao = this.baseMapper
				.selectOne(Wrappers.<SysUserDo>lambdaQuery().eq(SysUserDo::getUsername,username));
		UserVo user = SysUserConvert.INSTANCE.convertUserDetails(dao);
		user.setEnabled(true);
		user.setExpired(false);
		user.setLocked(false);
		user.setPasswordExpired(false);
		user.setRoles(Collections.singletonList(defaultRole()));
		return user;
	}

	@Override
	public UserVo findUserByMobile(String mobile) {
		SysUserDo dao = this.baseMapper
				.selectOne(Wrappers.<SysUserDo>lambdaQuery().eq(SysUserDo::getMobile, mobile));
		UserVo user = SysUserConvert.INSTANCE.convertUserDetails(dao);
		user.setEnabled(true);
		user.setExpired(false);
		user.setLocked(false);
		user.setPasswordExpired(false);
		user.setRoles(Collections.singletonList(defaultRole()));
		return user;
	}


	@Override
	public UserVo findUserByEmail(String email) {
		SysUserDo dao = this.baseMapper
				.selectOne(Wrappers.<SysUserDo>lambdaQuery().eq(SysUserDo::getEmail, email));
		UserVo user = SysUserConvert.INSTANCE.convertUserDetails(dao);
		user.setEnabled(true);
		user.setExpired(false);
		user.setLocked(false);
		user.setPasswordExpired(false);
		user.setRoles(Collections.singletonList(defaultRole()));
		return user;
	}

	@Override
	public SysUserVo getVo(final String id) {
		final SysUserDo userDo = this.getById(id);
		SysUserVo userVo = SysUserConvert.INSTANCE.convert(userDo);
		return userVo;
	}

	@Override
	public Boolean update(final SysUserVo vo) {
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
		SysUserDo userDo = SysUserConvert.INSTANCE.convert(vo);
		return this.updateById(userDo);
	}

	@Override
	public Boolean add(final SysUserVo vo) {
		SysUserDo userDo = SysUserConvert.INSTANCE.convert(vo);
		//密码进行加密
		userDo.setPassword(encoder.encode(userDo.getPassword()));
		userDo.setLoginCount(0);
		userDo.setLoginErrorCount(0);
		userDo.setLoginTime(LocalDateTime.now());
		return this.save(userDo);
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
	public IPage<SysUserVo> selectPageVo(final Page page) {
		final IPage<SysUserDo> iPage = this.page(page);
		return iPage.convert(SysUserVo::new);
	}

	private SysRoleVo defaultRole() {
		return new SysRoleVo().setRemark(
				CommonConstants.ROLE_DEFAULT)
				.setOperations(Collections.singletonList(new Operation(CommonConstants.OP_DEFAULT)));
	}
}
