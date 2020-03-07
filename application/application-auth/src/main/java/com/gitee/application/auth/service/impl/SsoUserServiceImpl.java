package com.gitee.application.auth.service.impl;

import java.time.LocalDateTime;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gitee.application.auth.convert.SsoUserConvert;
import com.gitee.application.auth.mapper.SsoUserMapper;
import com.gitee.application.auth.service.SsoUserService;
import com.gitee.common.core.constant.CacheConstants;
import com.gitee.common.core.constant.CommonConstants;
import com.gitee.common.data.redis.CustomRedisRepository;
import com.gitee.common.security.dao.SsoUserDAO;
import com.gitee.common.security.vo.Operation;
import com.gitee.common.security.vo.SsoRoleVO;
import com.gitee.common.security.vo.SsoUserVO;
import com.gitee.common.security.vo.UserVO;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 */
@Slf4j
@Service
public class SsoUserServiceImpl extends ServiceImpl<SsoUserMapper, SsoUserDAO> implements SsoUserService {

	@Autowired
	private CustomRedisRepository redisRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public UserVO findUserByUsername(String username) {
		SsoUserDAO dao = this.baseMapper
				.selectOne(Wrappers.<SsoUserDAO>lambdaQuery().eq(SsoUserDAO::getUsername, username));
		UserVO user = SsoUserConvert.INSTANCE.convertUserDetails(dao);
		user.setEnabled(true);
		user.setExpired(false);
		user.setLocked(false);
		user.setPasswordExpired(false);
		user.setRoles(Collections.singletonList(defaultRole()));
		return user;
	}

	@Override
	public UserVO findUserByMobile(String mobile) {
		SsoUserDAO dao = this.baseMapper
				.selectOne(Wrappers.<SsoUserDAO>lambdaQuery().eq(SsoUserDAO::getMobile, mobile));
		UserVO user = SsoUserConvert.INSTANCE.convertUserDetails(dao);
		user.setEnabled(true);
		user.setExpired(false);
		user.setLocked(false);
		user.setPasswordExpired(false);
		user.setRoles(Collections.singletonList(defaultRole()));
		return user;
	}

	@Override
	public SsoUserVO getVo(final String id) {
		final SsoUserDAO userDAO = this.getById(id);
		SsoUserVO userVO = SsoUserConvert.INSTANCE.convert(userDAO);
		return userVO;
	}

	@Override
	public Boolean update(final SsoUserVO vo) {
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
		SsoUserDAO userDAO = SsoUserConvert.INSTANCE.convert(vo);
		return this.updateById(userDAO);
	}

	@Override
	public Boolean add(final SsoUserVO vo) {
		SsoUserDAO userDAO = SsoUserConvert.INSTANCE.convert(vo);
		//密码进行加密
		userDAO.setPassword(encoder.encode(userDAO.getPassword()));
		userDAO.setLoginCount(0);
		userDAO.setLoginErrorCount(0);
		userDAO.setLoginTime(LocalDateTime.now());
		return this.save(userDAO);
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
	public IPage<SsoUserVO> selectPageVo(final Page page) {
		final IPage<SsoUserDAO> iPage = this.page(page);
		return iPage.convert(SsoUserVO::new);
	}

	private SsoRoleVO defaultRole() {
		return new SsoRoleVO().setRemark(
				CommonConstants.ROLE_DEFAULT)
				.setOperations(Collections.singletonList(new Operation(CommonConstants.OP_DEFAULT)));
	}
}
