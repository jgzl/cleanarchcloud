package com.github.jgzl.infra.upms.service.impl;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.jgzl.infra.upms.convert.SysUserConvert;
import com.github.jgzl.infra.upms.mapper.SysUserMapper;
import com.github.jgzl.infra.upms.service.SysUserService;
import com.github.jgzl.common.cache.support.CustomRedisRepository;
import com.github.jgzl.common.core.constant.CacheConstants;
import com.github.jgzl.common.core.constant.CommonConstants;
import com.github.jgzl.common.api.dataobject.SysUser;
import com.github.jgzl.common.api.vo.Operation;
import com.github.jgzl.common.api.vo.SysRoleVo;
import com.github.jgzl.common.api.vo.SysUserVo;
import com.github.jgzl.common.api.vo.UserVo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Collections;

/**
 * @author lihaifeng
 */
@Slf4j
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	private final CustomRedisRepository redisRepository;

	private final PasswordEncoder encoder;

	public UserVo findByTypeAndTypeValue(SFunction<SysUser,String> function, String value) {
		SysUser dao = this.baseMapper
				.selectOne(Wrappers.<SysUser>lambdaQuery().eq(function,value));
		if (dao==null) {
			return null;
		}
		UserVo user = SysUserConvert.INSTANCE.convertUserDetails(dao);
		user.setEnabled(true);
		user.setExpired(false);
		user.setLocked(false);
		user.setPasswordExpired(false);
		user.setRoles(Collections.singletonList(defaultRole()));
		return user;
	}

	@Override
	public UserVo findUserByUsername(String username) {
		return findByTypeAndTypeValue(SysUser::getUsername,username);
	}

	@Override
	public UserVo findUserByMobile(String mobile) {
		return findByTypeAndTypeValue(SysUser::getMobile,mobile);
	}

	@Override
	public UserVo findUserByEmail(String email) {
		return findByTypeAndTypeValue(SysUser::getEmail,email);
	}

	@Override
	public SysUserVo getVo(final String id) {
		final SysUser userDo = this.getById(id);
		if (userDo==null) {
			return null;
		}
		return SysUserConvert.INSTANCE.convert(userDo);
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
		SysUser userDo = SysUserConvert.INSTANCE.convert(vo);
		return this.updateById(userDo);
	}

	@Override
	public Boolean add(final SysUserVo vo) {
		SysUser userDo = SysUserConvert.INSTANCE.convert(vo);
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
		final IPage<SysUser> iPage = this.page(page);
		return iPage.convert(SysUserVo::new);
	}

	private SysRoleVo defaultRole() {
		return new SysRoleVo().setRemark(
				CommonConstants.ROLE_DEFAULT)
				.setOperations(Collections.singletonList(new Operation(CommonConstants.OP_DEFAULT)));
	}
}
